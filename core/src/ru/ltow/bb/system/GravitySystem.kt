package ru.ltow.bb.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.ashley.utils.ImmutableArray
import ru.ltow.bb.component.Grounded

class GravitySystem(
    i: Float
): IntervalSystem(i) {
    lateinit var falling: ImmutableArray<Entity>
    lateinit var grounded: ImmutableArray<Entity>

    override fun addedToEngine(e: Engine) {
        falling = e.getEntitiesFor(Family.exclude(Grounded::class.java).get())
        grounded = e.getEntitiesFor(Family.all(Grounded::class.java).get())
    }

    override fun updateInterval() {
        falling.forEach {
            //process gravity, add Grounded if grounded
            it.add(Grounded())
        }
        grounded.forEach {
            //check if started falling? do nothing?
        }
    }
}