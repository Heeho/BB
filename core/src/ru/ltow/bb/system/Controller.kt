package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import ru.ltow.bb.component.Player
import ru.ltow.bb.component.State

class Controller: EntitySystem() {
  private lateinit var playerEntities: ImmutableArray<Entity>
  private lateinit var stateMapper: ComponentMapper<State>

  override fun addedToEngine(engine: Engine?) {
    if(engine != null) {
      playerEntities = engine.getEntitiesFor(Family.one(Player::class.java).get())
      stateMapper = ComponentMapper.getFor(State::class.java)
    }
    super.addedToEngine(engine)
  }

  override fun update(deltaTime: Float) {
    /*
    Gdx.*
    touchDown
    touchDragged
    touchUp
     */
  }

  fun onDragStart() {
    playerEntities.forEach {
      stateMapper.get(it).start(State.Action.WALK)
    }
  }
  fun onDragStop() {
    playerEntities.forEach {
      stateMapper.get(it).end(State.Action.WALK)
    }
  }
}
