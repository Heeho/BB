package ru.ltow.bb

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.Family
import ru.ltow.bb.component.Animation
import ru.ltow.bb.component.Stand
import ru.ltow.bb.component.Walk

class AnimationListeners (
  engine: Engine
) {
  init {
    engine.addEntityListener(
      Family.all(Animation::class.java, Stand::class.java).get(), //.not(Use,Attack)),
      object: EntityListener {
        override fun entityAdded(e: Entity) {
          Mappers.animation.get(e).set(AnimationSystem.Action.STAND)
        }
        override fun entityRemoved(e: Entity) {}
      }
    )
    engine.addEntityListener(
      Family.all(Animation::class.java, Walk::class.java).get(), //.not(Attack)),
      object: EntityListener {
        override fun entityAdded(e: Entity) {
          Mappers.animation.get(e).set(AnimationSystem.Action.WALK)
        }
        override fun entityRemoved(e: Entity) {}
      }
    )
  }
}