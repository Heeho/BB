package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import ru.ltow.bb.component.State

class StateMachine: EntitySystem() {
  private lateinit var stateEntities: ImmutableArray<Entity>
  private lateinit var stateMapper: ComponentMapper<State>

  override fun addedToEngine(engine: Engine?) {
    if (engine != null) {
      stateEntities = engine.getEntitiesFor(Family.one(State::class.java).get())
      stateMapper = ComponentMapper.getFor(State::class.java)
    }
    super.addedToEngine(engine)
  }

  override fun update(delta: Float) {
    stateEntities.forEach { e ->
      stateMapper.get(e).let {
        it.actions().forEach { a ->
          a.act(it)
        }
      }
    }
  }
}
