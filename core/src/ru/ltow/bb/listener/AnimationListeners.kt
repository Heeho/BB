package ru.ltow.bb.listener

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.Family
import ru.ltow.bb.component.*
import ru.ltow.bb.system.AnimationSystem

class AnimationListeners (
  engine: Engine
) {
  init {
    engine.addEntityListener(
      Family.all(Animation::class.java, Stand::class.java).exclude(Use::class.java, Attack::class.java).get(),
      object: EntityListener {
        override fun entityAdded(e: Entity) {
          Mappers.animation.get(e).set(AnimationSystem.Action.STAND)
        }
        override fun entityRemoved(e: Entity) {}
      }
    )
    engine.addEntityListener(
      Family.all(Animation::class.java, Walk::class.java).exclude(Attack::class.java).get(),
      object: EntityListener {
        override fun entityAdded(e: Entity) {
          Mappers.animation.get(e).set(AnimationSystem.Action.WALK)
        }
        override fun entityRemoved(e: Entity) {}
      }
    )
  }
}