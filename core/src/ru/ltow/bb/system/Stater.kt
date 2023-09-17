package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.signals.Signal
import com.badlogic.ashley.utils.ImmutableArray
import ru.ltow.bb.Camera
import ru.ltow.bb.component.State

class Stater(
    val camera: Camera,
    val stateChanged: Signal<Entity>
): EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>
    private lateinit var stateMapper: ComponentMapper<State>

    override fun addedToEngine(engine: Engine?) {
        if(engine != null) {
            entities = engine.getEntitiesFor(Family.all(State::class.java).get())
            stateMapper = ComponentMapper.getFor(State::class.java)
        }
        super.addedToEngine(engine)
    }

    override fun update(deltaTime: Float) {
        entities.forEach {entity ->
            if(newFace(entity) || newState(entity))
                stateChanged.dispatch(entity)
        }
        super.update(deltaTime)
    }

    private fun newFace(e: Entity): Boolean {
        // f( stateMapper.get(e).face, camera)
        return false
    }
    private fun newState(e: Entity): Boolean {
        // f( stateMapper.get(e).value, Motion(if any), etc. )
        return false
    }
}