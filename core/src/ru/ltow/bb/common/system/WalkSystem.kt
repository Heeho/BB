package ru.ltow.bb.common.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.ashley.utils.ImmutableArray
import ru.ltow.bb.client.component.Billboard
import ru.ltow.bb.common.component.Creature
import ru.ltow.bb.common.component.Grounded
import ru.ltow.bb.common.component.Mappers
import ru.ltow.bb.common.component.Walk

class WalkSystem(i: Float): IntervalSystem(i) {
    lateinit var walks: ImmutableArray<Entity>

    override fun addedToEngine(e: Engine) {
        walks = e.getEntitiesFor(Family.all(Creature::class.java, Billboard::class.java, Walk::class.java, Grounded::class.java).get())
    }

    override fun updateInterval() {
        walks.forEach { e ->
            val v = Mappers.walk.get(e).vector.cpy().scl(Mappers.creature.get(e).speed * interval)
            Mappers.billboard.get(e).translate(v)
        }
    }
}