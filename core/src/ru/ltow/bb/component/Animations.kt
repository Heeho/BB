package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import ru.ltow.bb.system.StateMachine
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Animations(
    private val animations: HashMap<
        Pair<StateMachine.State,StateMachine.Face>,
            Animation<TextureRegion>
    >
): Component {
    private var stateTime = 0f
    private var current = animations[Pair(StateMachine.State.STAND,StateMachine.Face.SW)]!!

    fun getKeyFrame(delta: Float): TextureRegion {
        stateTime += delta
        return current.getKeyFrame(stateTime)
    }
}