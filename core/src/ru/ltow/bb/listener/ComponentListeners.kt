package ru.ltow.bb

class ComponentListeners (
  engine: Engine,
) {
  val standers = engine.getEntitiesFor(Family.all(Animation,Face,Stand)), //.not(Use,Attack)),
  val walkers = engine.getEntitiesFor(Family.all(Animation,Face,Walk)), //.not(Attack)),

  init {
    engine.addEntityListener(
      standers,
      AnimationListener(Action.STAND)
    )

    engine.addEntityListener(
      walkers,
      AnimationListener(Action.WALK)
    )
    engine.addEntityListener(
      walkers,
      FaceListener { e -> Mappers.walk.get(e).vector }
    )
  }

  class AnimationListener(
    val ac: Action
  ): EntityListener {
    override fun entityAdded(e: Entity) {
      val a = Mappers.animation.get(e)
      a.current.set(a.pack[ac])
    }
    override fun entityRemoved(e: Entity) {}
  }

  class FaceListener(
    val v: (Entity) -> Vector3
  ): EntityListener {
    override fun entityAdded(e: Entity) {
      Mappers.face.get(e).vector = v(e)
    }
    override fun entityRemoved(e: Entity) {}
  }
}