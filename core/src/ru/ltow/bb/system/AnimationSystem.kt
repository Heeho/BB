package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ru.ltow.bb.component.Face
import ru.ltow.bb.component.Animation as AnimationC
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ArrayMap
import com.badlogic.gdx.graphics.g2d.Animation
import ru.ltow.bb.component.*

class AnimationSystem (
  val atlas: TextureAtlas,
  val creaturedata: Array<Creature>,
  i: Float
): IntervalSystem(i) {
  enum class Action { STAND,WALK,FALL,USE,ATTACK }
  
  val ANIMATION_FRAME_DURATION = .2f
  lateinit var animations: ImmutableArray<Entity>
  lateinit var packs: ArrayMap<String,ArrayMap<Action,ArrayMap<FaceSystem.Face,Animation<TextureRegion>>>>
  lateinit var creatures: ImmutableArray<Entity>

  override fun addedToEngine(e: Engine) {
    animations = e.getEntitiesFor(Family.all(AnimationC::class.java, Face::class.java, Billboard::class.java).get())
    creatures = e.getEntitiesFor(Family.all(Creature::class.java).exclude(AnimationC::class.java,Face::class.java).get())
    packs = creatures(creaturedata)

    e.addEntityListener(
      Family.all(AnimationC::class.java, Stand::class.java).exclude(Use::class.java, Attack::class.java).get(),
      object: EntityListener {
        override fun entityAdded(e: Entity) {
          Mappers.animation.get(e).setpack(Action.STAND)
        }
        override fun entityRemoved(e: Entity) {}
      }
    )
    e.addEntityListener(
      Family.all(AnimationC::class.java, Walk::class.java).exclude(Attack::class.java).get(),
      object: EntityListener {
        override fun entityAdded(e: Entity) {
          Mappers.animation.get(e).setpack(Action.WALK)
        }
        override fun entityRemoved(e: Entity) {}
      }
    )

    e.addEntityListener(
      Family.all(Face::class.java,Walk::class.java).exclude(Attack::class.java).get(),
      object: EntityListener {
        override fun entityAdded(e: Entity) {
          Mappers.face.get(e).vector = Mappers.walk.get(e).vector
        }
        override fun entityRemoved(e: Entity) {}
      }
    )
  }

  override fun updateInterval() {
    creatures.forEach { e ->
      val c = Mappers.creature.get(e)
      val a = ru.ltow.bb.component.Animation(packs[c.name])
      val f = Face()
      val b = Billboard(c.position,a.frame())
      e.add(a).add(f).add(b)
    }
    animations.forEach { e ->
      Mappers.billboard.get(e).decal.textureRegion = Mappers.animation.get(e).frame(interval)
    }
  }

  private fun getAtlasRegions(path: String): Array<TextureAtlas.AtlasRegion> {
    val regions = atlas.findRegions(path)
    if(regions.isEmpty) throw IllegalArgumentException("couldn't find regions: $path")
    if(regions.contains(null)) throw IllegalArgumentException("couldn't find some regions: $path")
    return regions
  }

  private fun creatures(c: Array<Creature>): ArrayMap<String,ArrayMap<Action,ArrayMap<FaceSystem.Face,Animation<TextureRegion>>>> {
    val newpack = ArrayMap<String,ArrayMap<Action,ArrayMap<FaceSystem.Face,Animation<TextureRegion>>>>()
    c.forEach { creature ->
      if(newpack.put(creature.name,ArrayMap()) != 0) throw IllegalArgumentException("duplicate name: {$creature.name}")
      Action.values().forEach { action ->
        newpack[creature.name].put(action,ArrayMap())
        FaceSystem.Face.values().forEach { face ->
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
}