package ru.ltow.bb.listener

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.Family
import ru.ltow.bb.component.*

class ComponentListeners (
  engine: Engine
) {
  init {
    engine.addEntityListener(
      Family.exclude(Stand::class.java,Walk::class.java).get(),
      object: EntityListener {
        override fun entityAdded(e: Entity) {
          e.add(Stand())
        }
        override fun entityRemoved(e: Entity) {}
      }
    )
    engine.addEntityListener(
      Family.all(Walk::class.java).get(),
      object: EntityListener {
        override fun entityAdded(e: Entity) {
          e.remove(Stand::class.java)
        }
        override fun entityRemoved(e: Entity) {
          e.add(Stand())
        }
      }
    )
  }
}