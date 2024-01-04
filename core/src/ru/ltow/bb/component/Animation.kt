package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.ArrayMap
import ru.ltow.bb.system.AnimationSystem

class Animation(
  private val pack: ArrayMap<AnimationSystem.Action, ArrayMap<AnimationSystem.Face, Animation<TextureRegion>>>
): Component {
  private var time = 0f
  private var current: ArrayMap<AnimationSystem.Face,Animation<TextureRegion>>

  init {
    current = pack[AnimationSystem.Action.STAND]
  }

  fun set(a: AnimationSystem.Action) {
    time = 0f
    current = pack[a]
  }

  fun frame(
    f: AnimationSystem.Face,
    dt: Float
  ): TextureRegion {
    time += dt
    return current[f].getKeyFrame(time)
  }
}
