package ru.ltow.bb

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import ru.ltow.bb.animation.AnimationBase
import ru.ltow.bb.component.Animated
import ru.ltow.bb.component.Model
import ru.ltow.bb.component.Billboard
import ru.ltow.bb.component.Player

class EntityFactory(
    private val atlas: TextureAtlas
) {
    private val ANIMATION_FRAME_DURATION = 200f
    fun cube(): Entity {
        return Entity().apply {
            add(Model(ModelBuilder().createBox(
                1f, 1f, 1f,
                Material(ColorAttribute.createDiffuse(Color.BLUE), BlendingAttribute()),
                VertexAttributes.Usage.Position.toLong() or VertexAttributes.Usage.Normal.toLong()
            )))
        }
    }

    fun player(): Entity = toad().apply { add(Player()) }
    fun toad(): Entity = Entity()
        .add(Billboard(
            0f,0f,0f,
            atlas.findRegion("creature/toad/stand/sw/",1)
        ))
        .add(
            Animated(AnimationBase(atlas.findRegions("creature/toad/stand/sw/")))
        )
}