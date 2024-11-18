package ru.ltow.bb.common.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class TotalVelocity(
    var speed: Float
): Component {
    val vector = Vector2()
}