package ru.ltow.bb

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import ru.ltow.bb.manager.EntityFactory
import ru.ltow.bb.system.Renderer

class World: Disposable {
    private val prefs = Gdx.app.getPreferences("prefs")
    private val background = Color(prefs.getInteger("COLOR_BACKGROUND", 0x00ffffff.toInt()))
    private val ambientLightColor = Color(prefs.getInteger("COLOR_LIGHT_AMBIENT", 0xffffffff.toInt()))
    private val directionalLightColor = Color(prefs.getInteger("COLOR_LIGHT_DIRECTIONAL", 0xffffffff.toInt()))

    val camera: Camera
    val viewport: FitViewport
    private val environment = Environment()

    private val groupStrategy: CameraGroupStrategy
    private val decalBatch: DecalBatch
    private val modelBatch: ModelBatch
    private val atlas: TextureAtlas

    private val entityFactory: EntityFactory
    val engine: Engine

    init {
        val viewportSize = 10f
        camera = Camera (
            viewportSize,66f,1f,10000f,
            Vector3(0f,0f,10f),
            Vector3(0f,0f,0f),
            prefs.getFloat("CAMERA_ROTATE_SENSIVITY", 1f)
        )
        viewport = FitViewport(viewportSize,viewportSize,camera)

        initEnvironment()

        groupStrategy = CameraGroupStrategy(camera)
        decalBatch = DecalBatch(groupStrategy)
        modelBatch = ModelBatch()
        atlas = TextureAtlas(Gdx.files.internal("sprite/sprite.atlas"))
        println(atlas.regions)
        engine = Engine()
        engine.addSystem(Renderer(camera,viewport,modelBatch,decalBatch,background,environment))

        entityFactory = EntityFactory(atlas)

        /*http://TEST*/
        //engine.addEntity(EntityFactory.cube())
        engine.addEntity(entityFactory.toad())

        viewport.update(Gdx.graphics.width, Gdx.graphics.height)
    }

    private fun initEnvironment() {
        val directionalLight = DirectionalLight()
        environment.set(ColorAttribute(ColorAttribute.AmbientLight,ambientLightColor))
        directionalLight.color.set(directionalLightColor)
        directionalLight.direction.set(camera.direction)
        environment.add(directionalLight)
    }

    override fun dispose() {
        modelBatch.dispose()
        decalBatch.dispose()
        groupStrategy.dispose()
        atlas.dispose()
    }
}