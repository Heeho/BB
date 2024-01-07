package ru.ltow.bb.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.ashley.utils.ImmutableArray
import ru.ltow.bb.component.*

class WalkSystem(i: Float): IntervalSystem(i) {
    lateinit var walks: ImmutableArray<Entity>

    override fun addedToEngine(e: Engine) {
        walks = e.getEntitiesFor(Family.all(Billboard::class.java, Walk::class.java,Creature::class.java).get())

        e.addEntityListener(
            Family.exclude(Stand::class.java,Walk::class.java).get(),
            object: EntityListener {
                override fun entityAdded(e: Entity) {
                    e.add(Stand())
                }
                override fun entityRemoved(e: Entity) {}
            }
        )
        e.addEntityListener(
            Family.all(Walk::class.java).get(),
            object: EntityListener {
                override fun entityAdded(e: Entity) {
                    e.remove(Stand::class.java)
                }
                override fun entityRemoved(e: Entity) {
                    e.add(Stand())
                }
            }
        )
    }

    override fun updateInterval() {
        walks.forEach { e ->
            val v = Mappers.walk.get(e).vector.cpy().nor().scl(Mappers.creature.get(e).speed * interval)
            Mappers.billboard.get(e).translate(v)
        }
    }
}