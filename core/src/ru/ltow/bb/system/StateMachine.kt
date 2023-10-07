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
        entities.forEach { e =>
            val s = stateMapper.get(e)
            val cur = s.current
            val new = nextStateFrom(e,s)
            if(cur == new) s.time += delta
            else {
                all[cur].remove(e)
                all[new].add(e)
                s.current = new
                s.time = 0f
            }
        }
    }
    
    private fun nextStateFrom(e: Entity, s: State): State {
        when(s.current) {
            STAND => {
                if(attacking(e)) ATTACK
                else if(falling(e)) FALL
                else if(walking(e)) WALK
                else if(acting(e)) ACT
                else STAND
            }
            WALK => {
                if(attacking(e)) ATTACK
                else if(falling(e)) FALL
                else if(!walking(e)) STAND
                else WALK
            }
            FALL => {
                if(attacking(e)) ATTACK
                if(!falling(e)) STAND
            }
            ACT => {
                if(attacking(e)) ATTACK
                else if(falling(e)) FALL
                else if(walking(e)) WALK
                else if(!acting(e)) STAND
                else ACT
            }
            ATTACK => {
                if(!attacking(e)) {
                    if(falling(e)) FALL
                    else if(walking(e)) WALK
                    else STAND
                }
                else ATTACK
            }
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
