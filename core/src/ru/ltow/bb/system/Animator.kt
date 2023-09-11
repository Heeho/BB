package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.signals.Listener
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ru.ltow.bb.component.Billboard
import com.badlogic.gdx.utils.Array

class Animator(
    val atlas: TextureAtlas,
    val facingChanged: Listener<Entity>,
    val stateChanged: Listener<Entity>
): EntitySystem() {
    enum class State { STAND,WALK,LIFTED }
    enum class Face { SW,SE,NE,NW }
    private val spritePacks = HashMap<String,HashMap<Pair<State,Face>,Array<TextureAtlas.AtlasRegion>>>()

    private lateinit var billboards: ImmutableArray<Entity>
    private lateinit var billboardMapper: ComponentMapper<Billboard>

    override fun addedToEngine(engine: Engine?) {
        if(engine != null) {
            billboards = engine.getEntitiesFor(Family.all(Billboard::class.java).get())
            billboardMapper = ComponentMapper.getFor(Billboard::class.java)
        }
        super.addedToEngine(engine)
    }

    init {
        val name = "toad"
        var path :String
        var regions: Array<TextureAtlas.AtlasRegion>

        if (spritePacks.put(name,HashMap()) != null) throw IllegalArgumentException("повторение $name")

        State.values().forEach { state ->
            Face.values().forEach { face ->
                path = "creatures/$name/${state.name}/${face.name}"
                regions = atlas.findRegions(path)
                if(regions.isEmpty) throw IllegalArgumentException("отсутствуют регионы $path")
                spritePacks[name]?.put(Pair(state,face),regions)
            }
        }
    }

    override fun update(deltaTime: Float) {

    }
}