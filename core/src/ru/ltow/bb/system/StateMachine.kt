package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Vector2

class StateMachine: EntitySystem() {
    enum class State { STAND,WALK,FALL }
    enum class Face { SW,SE,NE,NW }

    private lateinit var entities: ImmutableArray<Entity>
    private lateinit var stateMapper: ComponentMapper<ru.ltow.bb.component.State>

    override fun addedToEngine(engine: Engine?) {
        if(engine != null) {
            entities = engine.getEntitiesFor(Family.all(ru.ltow.bb.component.State::class.java).get())
            stateMapper = ComponentMapper.getFor(ru.ltow.bb.component.State::class.java)
        }
        super.addedToEngine(engine)
    }

    override fun update(deltaTime: Float) {
        /*entities.forEach {
            if(newFace(it) || newState(it))
                stateMapper.get(it).current = State.WALK
        }*/
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

    companion object {
        fun calculateFace(v: Vector2): Face {
            return if (v.x >= 0 && v.y >= 0) Face.NE
            else if (v.x >= 0 && v.y < 0) Face.SE
            else if (v.x < 0 && v.y >= 0) Face.NW
            else Face.SW
        }
    }
}