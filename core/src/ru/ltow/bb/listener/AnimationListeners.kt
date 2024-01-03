package ru.ltow.bb

class AnimationListeners (
  engine: Engine
) {
  init {
    engine.addEntityListener(
      engine.getEntitiesFor(Family.all(Animation,Stand)), //.not(Use,Attack)),
      EntityListener {
        override fun entityAdded(e: Entity) {
          Mappers.animation.get(e).set(AnimationSystem.Action.STAND)
        }
        override fun entityRemoved(e: Entity) {}
      }
    )
    engine.addEntityListener(
      engine.getEntitiesFor(Family.all(Animation,Walk)), //.not(Attack)),
      EntityListener {
        override fun entityAdded(e: Entity) {
          Mappers.animation.get(e).set(AnimationSystem.Action.WALK)
        }
        override fun entityRemoved(e: Entity) {}
      }
    )
  }
}