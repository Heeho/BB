package ru.ltow.bb

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
import com.badlogic.gdx.utils.Array
import ru.ltow.bb.component.*

class EntityFactory(
    private val atlas: TextureAtlas
) {
    val ANIMATION_FRAME_DURATION = .2f

    private val animations = HashMap<
        String,
        HashMap<
            Pair<State.Action,State.Face>,
            Animation<TextureRegion>
        >
    >()

    init {
        val name = "toad"

        if (animations.put(name,HashMap()) != null) throw IllegalArgumentException("duplicate name: $name")

        State.Action.values().forEach { state ->
            State.Face.values().forEach { face ->
                animations[name]?.put(
                    Pair(state,face),
                    Animation(
                        ANIMATION_FRAME_DURATION,
                        getAtlasRegions("creature/$name/${state.name}/${face.name}/"),
                        Animation.PlayMode.LOOP
                    )
                )
            }
        }
    }

    fun cube(): Entity {
        return Entity().apply {
            add(Model(ModelBuilder().createBox(
                1f, 1f, 1f,
                Material(ColorAttribute.createDiffuse(Color.BLUE), BlendingAttribute()),
                VertexAttributes.Usage.Position.toLong() or VertexAttributes.Usage.Normal.toLong()
            )))
        }
    }

    fun player(): Entity = toad().apply { add(Player(this.getComponent(State::class.java))) }

    fun toad(): Entity {
        val e = Entity()
        val b = Billboard(
            0f,0f,0f,
            animations["toad"]!![Pair(State.Action.STAND,State.Face.SW)]!!.getKeyFrame(0f)
        )
        val m = Motion()
        val a = Animations(animations["toad"]!!)

        return e.add(b).add(m).add(a).apply { this.add(State(b, m)) }
    }

    private fun getAtlasRegions(path: String): Array<TextureAtlas.AtlasRegion> {
        val regions = atlas.findRegions(path)
        if(regions == null || regions.isEmpty) throw IllegalArgumentException("couldn't find regions: $path")
        return regions
    }
}