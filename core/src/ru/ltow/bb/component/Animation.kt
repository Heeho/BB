package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ru.ltow.bb.component.State.Action
import ru.ltow.bb.component.Direction.Face

class Animation: Component {
    val animationPack = HashMap<Pair<Action,Face>,Animation<TextureRegion>>()

    fun getFrame(delta: Float, action: Action, face: Face): TextureRegion {
        return animationPack[Pair(action,face)]?.getKeyFrame(delta) ?: throw Exception("в наборе отсутствует анимация: ${action.name}, ${face.name}")
    }
}