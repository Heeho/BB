package ru.ltow.bb.listener

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.Family
import ru.ltow.bb.component.*

class FaceListeners (
  engine: Engine
) {
  init {
    engine.addEntityListener(
      Family.all(Face::class.java,Walk::class.java).exclude(Attack::class.java).get(),
      object: EntityListener {
        override fun entityAdded(e: Entity) {
          Mappers.face.get(e).vector = Mappers.walk.get(e).vector
        }
        override fun entityRemoved(e: Entity) {}
      }
    )
  }
}