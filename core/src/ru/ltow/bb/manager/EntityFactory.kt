package ru.ltow.bb.manager

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.utils.Json
import ru.ltow.bb.component.State.Action
import ru.ltow.bb.component.Direction.Face
import ru.ltow.bb.component.Model
import ru.ltow.bb.component.Billboard

class EntityFactory(
    val atlas: TextureAtlas
) {
    val spritePacks = HashMap<Pair<Action,Face>,TextureRegion>()
    val animationPacks = HashMap<Pair<Action,Face>,Animation<TextureRegion>>()

    companion object {
        fun cube(): Entity {
            val entity = Entity()
            val material = Material(ColorAttribute.createDiffuse(Color.BLUE), BlendingAttribute())
            val attributes = VertexAttributes.Usage.Position.toLong() or VertexAttributes.Usage.Normal.toLong()
            entity.add(Model(ModelBuilder().createBox(1f, 1f, 1f, material, attributes)))
            return entity
        }
    }

    fun toad(): Entity = Entity().apply { add(Billboard(frame = atlas.findRegion("toad/stand/south[0]"))) }

    fun creature(j: Json): Entity {
        val entity = Entity()
        //TODO Добавляем компоненты и инициализируем их. Для спрайтов и анимаций используем существующие наборы или создаем новые, используя атлас и данные из JSON
        return entity
    }
}