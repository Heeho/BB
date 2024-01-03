package ru.ltow.bb

class FaceListeners (
  engine: Engine
) {
  init {
    engine.addEntityListener(
      engine.getEntitiesFor(Family.all(Face,Walk)), //.not(Attack)),
      EntityListener {
        override fun entityAdded(e: Entity) {
          Mappers.face.get(e).vector = Mappers.walk.get(e).vector
        }
        override fun entityRemoved(e: Entity) {}
      }
    )
  }
}