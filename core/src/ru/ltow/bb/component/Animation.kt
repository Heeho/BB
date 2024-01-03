package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Animation(
  val pack: Map<
    AnimationSystem.Action,
    Map<
      AnimationSystem.Face,
      Animation<TextureRegion>
    >
  >
): Component {
  private var time = 0f
  private var current: Map<AnimationSystem.Face,Animation<TextureRegion>>
    set(value) {
      time = 0f
      field = value
    }

  fun frame(
    f: AnimationSystem.Face,
    dt: Float
  ) {
    time += dt
    current[f].getKeyFrame(time)
  }
}
