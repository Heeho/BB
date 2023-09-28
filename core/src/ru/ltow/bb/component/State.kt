package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import ru.ltow.bb.system.StateMachine

class State: Component {
    var current = StateMachine.State.STAND
}