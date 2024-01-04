package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ru.ltow.bb.component.Billboard
import ru.ltow.bb.component.Creature
import ru.ltow.bb.component.Face as FaceC
import ru.ltow.bb.component.Animation as AnimationC
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ArrayMap
import ru.ltow.bb.Camera
import ru.ltow.bb.component.Face
import ru.ltow.bb.component.Mappers
import com.badlogic.gdx.graphics.g2d.Animation

class AnimationSystem (
  val atlas: TextureAtlas,
  val creaturedata: Array<Creature>,
  val camera: Camera
): IntervalSystem(0.045f) {
  enum class Face { SE,SW,NW,NE }
  enum class Action { STAND,WALK,FALL,USE,ATTACK }
  
  val ANIMATION_FRAME_DURATION = .2f
  lateinit var animations: ImmutableArray<Entity>
  lateinit var packs: ArrayMap<String,ArrayMap<Action,ArrayMap<Face, Animation<TextureRegion>>>>
  lateinit var creatures: ImmutableArray<Entity>

  override fun addedToEngine(e: Engine) {
    animations = engine.getEntitiesFor(Family.all(AnimationC::class.java, FaceC::class.java, Billboard::class.java).get())
    creatures = engine.getEntitiesFor(Family.all(Creature::class.java).exclude(AnimationC::class.java,FaceC::class.java).get())
    packs = creatures(creaturedata)
  }

  override fun updateInterval() {
    creatures.forEach { e ->
      val c = Mappers.creature.get(e)
      val a = ru.ltow.bb.component.Animation(packs[c.name])
      val f = Face()
      val b = Billboard(c.position,a.frame(Face.SE,0f))
      e.add(a).add(f).add(b)
    }
    animations.forEach { e ->
      val a = Mappers.animation.get(e)
      Mappers.billboard.get(e).decal.textureRegion = a.frame(face(e),interval)
    }
  }

  private fun getAtlasRegions(path: String): Array<TextureAtlas.AtlasRegion> {
    val regions = atlas.findRegions(path)
    if(regions.isEmpty) throw IllegalArgumentException("couldn't find regions: $path")
    if(regions.contains(null)) throw IllegalArgumentException("couldn't find some regions: $path")
    return regions
  }

  private fun creatures(c: Array<Creature>): ArrayMap<String,ArrayMap<Action,ArrayMap<Face,Animation<TextureRegion>>>> {
    val newpack = ArrayMap<String,ArrayMap<Action,ArrayMap<Face,Animation<TextureRegion>>>>()
    c.forEach { creature ->
      if(newpack.put(creature.name,ArrayMap()) != 0) throw IllegalArgumentException("duplicate name: {$creature.name}")
      Action.values().forEach { action ->
        newpack[creature.name].put(action,ArrayMap())
        Face.values().forEach { face ->
          newpack[creature.name][action].put(
            face,
            Animation(
              ANIMATION_FRAME_DURATION,
              getAtlasRegions("creature/${creature.name}/${action.name}/${face.name}/"),
              Animation.PlayMode.LOOP
            )
          )
        }
      }
    }
    return newpack
  }

  private fun face(e: Entity): Face {
    val dv = Mappers.billboard.get(e).decal.position.cpy().sub(camera.position)
    val fv = Mappers.face.get(e).vector.cpy()

    val dot = dv.cpy().dot(fv)
    val crs = dv.cpy().crs(fv).y

    return if(dot >= 0 && crs >= 0) Face.NW
    else if(dot >= 0 && crs < 0) Face.NE
    else if(dot < 0 && crs >= 0) Face.SW
    else Face.SE
  }
}