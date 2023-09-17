package ru.ltow.bb.animation

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Array

class AnimationBase(
    keyFrames: Array<TextureAtlas.AtlasRegion>,
    frameDuration: Float = 100f
): Animation<TextureAtlas.AtlasRegion>(frameDuration,keyFrames) {

}