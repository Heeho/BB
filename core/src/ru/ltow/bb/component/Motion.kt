package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector3

class Motion(
    var speed: Float = 0f
): Component {
    private val vector = Vector3()

    fun velocity(): Vector3 = vector.cpy().apply { this.setLength(speed) }
}