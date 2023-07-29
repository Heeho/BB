package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class Direction: Component
{
    enum class Face { SOUTH, EAST, WEST, NORTH }
    var direction: Vector2 = Vector2(1f,0f)
}