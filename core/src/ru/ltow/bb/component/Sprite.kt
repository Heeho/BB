package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ru.ltow.bb.component.State.Action
import ru.ltow.bb.component.Direction.Face

class Sprite: Component {
    val spritePack = HashMap<Pair<Action,Face>,TextureRegion>()

    fun getFrame(action: Action, face: Face): TextureRegion {
        return spritePack[Pair(action,face)] ?: throw Exception("в наборе отсутствует спрайт: ${action.name}, ${face.name}")
    }
}