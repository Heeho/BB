package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import ru.ltow.bb.component.Billboard
import com.badlogic.gdx.utils.Array
import ru.ltow.bb.animation.AnimationBase
import ru.ltow.bb.component.Animated
import ru.ltow.bb.component.Name
import ru.ltow.bb.component.State
import ru.ltow.bb.listener.AddToSetOnReceive

class Animator(
    val atlas: TextureAtlas,
    val animationInvalidated: AddToSetOnReceive<Entity>
): EntitySystem() {
    private val atlasRegions = HashMap<
        String,
        HashMap<
            Pair<State.Value,State.Face>,
            Array<AtlasRegion>
        >
    >()

    private lateinit var nameMapper: ComponentMapper<Name>
    private lateinit var stateMapper: ComponentMapper<State>
    private lateinit var animatedMapper: ComponentMapper<Animated>
    private lateinit var billboardMapper: ComponentMapper<Billboard>

    override fun addedToEngine(engine: Engine?) {
        if(engine != null) {
            nameMapper = ComponentMapper.getFor(Name::class.java)
            stateMapper = ComponentMapper.getFor(State::class.java)
            animatedMapper = ComponentMapper.getFor(Animated::class.java)
            billboardMapper = ComponentMapper.getFor(Billboard::class.java)
        }
        super.addedToEngine(engine)
    }

    init {
        val name = "toad"
        var regions: Array<AtlasRegion>

        if (atlasRegions.put(name,HashMap()) != null) throw IllegalArgumentException("повторение $name")

        State.Value.values().forEach { state ->
            State.Face.values().forEach { face ->
                regions = getAtlasRegions("creature/$name/${state.name}/${face.name}/")
                atlasRegions[name]?.put(Pair(state,face),regions)
            }
        }
    }

    override fun update(deltaTime: Float) {
        animationInvalidated.entities.forEach { e ->
            val state = stateMapper.get(e)
            val regions = getRegions(nameMapper.get(e).value,Pair(state.value,state.face))
            animatedMapper.get(e).setAnimation(AnimationBase(regions))
        }
        animationInvalidated.entities.clear()
        super.update(deltaTime)
    }

    private fun getRegions(name: String, key: Pair<State.Value,State.Face>): Array<AtlasRegion> {
        val regions = atlasRegions[name]?.get(key)
        regions ?: throw IllegalArgumentException("отсутствуют регионы $name/${key.first.name}/${key.second.name}/")
        return regions
    }

    private fun getAtlasRegions(path: String): Array<AtlasRegion> {
        val regions = atlas.findRegions(path)
        if(regions == null || regions.isEmpty) throw IllegalArgumentException("отсутствуют регионы $path")
        return regions
    }
}