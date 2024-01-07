package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.ArrayMap
import ru.ltow.bb.system.AnimationSystem
import ru.ltow.bb.system.FaceSystem

class Animation(
  private val pack: ArrayMap<AnimationSystem.Action, ArrayMap<FaceSystem.Face, Animation<TextureRegion>>>
): Component {
  private var time = 0f
  private var currentpack: ArrayMap<FaceSystem.Face,Animation<TextureRegion>>
  private var currentanimation: Animation<TextureRegion>

  init {
    currentpack = pack[AnimationSystem.Action.STAND]
    currentanimation = currentpack[FaceSystem.Face.SE]
  }

  fun setpack(a: AnimationSystem.Action) {
    time = 0f
    currentpack = pack[a]
  }

  fun setface(f: FaceSystem.Face) {
    currentanimation = currentpack[f]
  }

  fun frame(
    dt: Float = 0f
  ): TextureRegion {
    time += dt
    return currentanimation.getKeyFrame(time)
  }
}
