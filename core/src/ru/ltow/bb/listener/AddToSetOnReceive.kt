package ru.ltow.bb.listener

import com.badlogic.ashley.signals.Listener
import com.badlogic.ashley.signals.Signal

class AddToSetOnReceive<Entity>: Listener<Entity> {
    val entities = HashSet<Entity>()

    override fun receive(signal: Signal<Entity>?, e: Entity) {
        entities.add(e)
    }
}