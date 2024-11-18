package ru.ltow.bb.common.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector3

class Creature (
    val name: String,
    val position: Vector3 = Vector3(),
    val speed: Float = 1f
): Component