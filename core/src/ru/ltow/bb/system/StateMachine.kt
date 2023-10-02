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
    
    override fun update(delta: Float) {
        entities.forEach { e ->
            val s = stateMapper.get(e)
            s.time = if(e in stateInvalid) {
                if(changeState(e)) 0f
                else s.time + delta
            } else s.time + delta
        }
    }
    
    private fun changeState(e: Entity): Boolean {
        val s = stateMapper.get(e)
        return if(s.current == new)
        false
        else {
            all[s.current].remove(e)
            all[new].add(e)
            s.current = new
            true
        }
    }
  
    companion object {
        val all = emptyMap()
        init {
            enumValues<State>().forEach {
                all.add(it,emptyMutableArray())
            }
        }
        
        fun calculateFace(v: Vector2): Face {
            return if (v.x >= 0 && v.y >= 0) Face.NE
            else if (v.x >= 0 && v.y < 0) Face.SE
            else if (v.x < 0 && v.y >= 0) Face.NW
            else Face.SW
        }
    }
}
