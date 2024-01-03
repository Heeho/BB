package ru.ltow.bb

class ComponentListeners (
  engine: Engine
) {
  init {
    engine.addEntityListener(
      engine.getEntitiesFor(Family.not(Stand,Walk)),
      EntityListener {
        override fun entityAdded(e: Entity) {
          e.add(Stand())
        }
        override fun entityRemoved(e: Entity) {}
      }
    )
    engine.addEntityListener(
      engine.getEntitiesFor(Family.all(Walk)), //.not(Attack)),
      EntityListener {
        override fun entityAdded(e: Entity) {
          e.remove(Stand::java.class)
        }
        override fun entityRemoved(e: Entity) {
          e.add(Stand())
        }
      }
    )
  }
}