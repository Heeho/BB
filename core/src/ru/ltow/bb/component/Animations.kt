package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Animations(
    private val animations: HashMap<
        Pair<State.Action,State.Face>,
            Animation<TextureRegion>
    >
): Component {
    private var stateTime = 1f
    private var current = animations[Pair(State.Action.STAND,State.Face.SW)]!!

    fun getKeyFrame(delta: Float): TextureRegion {
        stateTime += delta
        return current.getKeyFrame(stateTime)
    }
}