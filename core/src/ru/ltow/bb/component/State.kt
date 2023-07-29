package ru.ltow.bb.component

import com.badlogic.ashley.core.Component

class State: Component {
    enum class Action { STAND, WALK, JUMP, ATTACK }
}