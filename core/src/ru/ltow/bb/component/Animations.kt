package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import ru.ltow.bb.system.StateMachine
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ru.ltow.bb.animation.AnimationBase

class Animations(
    private val animations: HashMap<
        Pair<StateMachine.State,StateMachine.Face>,
        AnimationBase
    >
): Component {
    private var current = animations[Pair(StateMachine.State.STAND,StateMachine.Face.SW)]!!

    fun getKeyFrame(delta: Float): TextureRegion = current.getKeyFrame(delta)
}