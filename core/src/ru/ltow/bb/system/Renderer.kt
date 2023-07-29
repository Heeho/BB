package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import ru.ltow.bb.component.Model
import ru.ltow.bb.component.Billboard

class Renderer(
    private val camera: PerspectiveCamera,
    private val viewport: FitViewport,
    private val modelBatch: ModelBatch,
    private val decalBatch: DecalBatch,
    private val background: Color,
    private val environment: Environment
): EntitySystem() {
    private lateinit var models: ImmutableArray<Entity>
    private lateinit var sprites: ImmutableArray<Entity>
    private lateinit var spriteMapper: ComponentMapper<Billboard>
    private lateinit var modelMapper: ComponentMapper<Model>

    override fun addedToEngine(engine: Engine?) {
        if(engine != null) {
            models = engine.getEntitiesFor(Family.one(Model::class.java).get())
            sprites = engine.getEntitiesFor(Family.one(Billboard::class.java).get())
            spriteMapper = ComponentMapper.getFor(Billboard::class.java)
            modelMapper = ComponentMapper.getFor(Model::class.java)
        }
        super.addedToEngine(engine)
    }

    override fun update(deltaTime: Float) {
        viewport.apply()
        camera.update()

        Gdx.gl.glClearColor(background.r, background.g, background.b, background.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        modelBatch.begin(camera)
        models.forEach { modelBatch.render(modelMapper.get(it).modelInstance, environment) }
        sprites.forEach { decalBatch.add(spriteMapper.get(it).decal.apply { lookAt(camera.position,camera.up) }) }
        modelBatch.end()
        decalBatch.flush()

        super.update(deltaTime)
    }
}