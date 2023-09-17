package ru.ltow.bb.component

import com.badlogic.ashley.core.Component

class State: Component {
    enum class Face { SW,SE,NE,NW }
    enum class Value { STAND,WALK,FALL }

    var face = Face.SW
    var value = Value.STAND
}