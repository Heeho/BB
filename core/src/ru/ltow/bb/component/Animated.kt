package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Animated(
    animation: Animation<TextureRegion>
): Component {
    private var looped: Animation<TextureRegion>? = null
    private var finite: Animation<TextureRegion>? = null

    val loopedAnimations = listOf(
        Animation.PlayMode.LOOP,
        Animation.PlayMode.LOOP_PINGPONG,
        Animation.PlayMode.LOOP_RANDOM,
        Animation.PlayMode.LOOP_REVERSED
    )

    init { setAnimation(animation) }

    fun setAnimation(a: Animation<TextureRegion>) {
        if (loopedAnimations.contains(a.playMode)) looped = a
        else finite = a
    }

    //fun getFrame(delta: Float): TextureRegion
}