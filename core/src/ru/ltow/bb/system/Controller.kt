package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import ru.ltow.bb.component.State

class Controller: EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>
    private lateinit var stateMapper: ComponentMapper<State>

    override fun addedToEngine(engine: Engine?) {
        if(engine != null) {
            entities = engine.getEntitiesFor(Family.all(State::class.java).get())
            stateMapper = ComponentMapper.getFor(State::class.java)
        }
        super.addedToEngine(engine)
    }
}