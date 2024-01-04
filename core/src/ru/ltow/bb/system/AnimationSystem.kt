package ru.ltow.bb

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector3
import ru.ltow.bb.component.Billboard
import ru.ltow.bb.input.Creature
import java.util.*
import ru.ltow.bb.component.Face as FaceC
import ru.ltow.bb.component.Animation as AnimationC
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ArrayMap

class AnimationSystem (
  val atlas: TextureAtlas,
  val creatures: Array<Creature>,
  val cameraLookAt: Vector3
): EntitySystem() {
  enum class Face { SE,SW,NW,NE }
  enum class Action { STAND,WALK,FALL,USE,ATTACK }
  
  val ANIMATION_FRAME_DURATION = .2f
  lateinit var animations: ImmutableArray<Entity>
  lateinit var packs: ArrayMap<String,ArrayMap<Action,ArrayMap<Face,Animation<TextureRegion>>>>

  override fun addedToEngine(e: Engine) {
    animations = engine.getEntitiesFor(Family.all(AnimationC::class.java, FaceC::class.java, Billboard::class.java).get())
    packs = creatures(creatures)
  }

  override fun update(dt: Float) {
    animations.forEach() { e ->
      val a = Mappers.animation.get(e)
      val f = Mappers.face.get(e)
      Mappers.billboard.get(e).decal.textureRegion = a.frame(face(f.vector),dt)
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

    c.forEach() { creature ->
      if(newpack.put(creature.name,ArrayMap()) != 0) throw IllegalArgumentException("duplicate name: {$creature.name}")

      Action.values().forEach { action ->
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

  private fun face(v: Vector3): Face {
    val dot = cameraLookAt.cpy().dot(v)
    val crs = cameraLookAt.cpy().crs(v).z

    return if(dot >= 0 && crs >= 0) Face.NE
    else if(dot >= 0 && crs < 0) Face.SE
    else if(dot < 0 && crs >= 0) Face.NW
    else Face.SW
  }
}