package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ru.ltow.bb.animation.AnimationBase

class Animated(
    private var animation: AnimationBase
): Component {
    val loopedAnimations = listOf(
        Animation.PlayMode.LOOP,
        Animation.PlayMode.LOOP_PINGPONG,
        Animation.PlayMode.LOOP_RANDOM,
        Animation.PlayMode.LOOP_REVERSED
    )

    init { animation.playMode = Animation.PlayMode.LOOP }

    fun setAnimation(a: AnimationBase) {
        animation = a
    }

    fun getKeyFrame(delta: Float): TextureRegion {
        return animation.getKeyFrame(delta)
    }
}