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
import ru.ltow.bb.system.Controller
import ru.ltow.bb.system.Renderer
import ru.ltow.bb.system.StateMachine

class World(
  val params: JSON = {"creatures":[{"name":"toad","speed":1}]}
  // существа, их характеристики и прочее 
): Disposable {
    private val prefs = Gdx.app.getPreferences("prefs")
    private val background = Color(prefs.getInteger("COLOR_BACKGROUND", 0x00ffffff.toInt()))
    private val ambientLightColor = Color(prefs.getInteger("COLOR_LIGHT_AMBIENT", 0xffffffff.toInt()))
    private val directionalLightColor = Color(prefs.getInteger("COLOR_LIGHT_DIRECTIONAL", 0xffffffff.toInt()))

    val camera: Camera
    val viewport: FitViewport
    private val environment = Environment()

    //DISPOSABLE
    private val modelBatch: ModelBatch
    private val decalBatch: DecalBatch
    private val groupStrategy: CameraGroupStrategy
    private val atlas: TextureAtlas

    private val entityFactory: EntityFactory
    val engine: Engine
    val walk: EntityListener

    init {
        //CAMERA
        val viewportSize = 10f
        camera = Camera(
            viewportSize, 66f, 1f, 10000f,
            Vector3(0f, 0f, 10f),
            Vector3(0f, 0f, 0f),
            prefs.getFloat("CAMERA_ROTATE_SENSIVITY", 1f)
        )
        viewport = FitViewport(viewportSize, viewportSize, camera)

        //ENVIRONMENT
        val directionalLight = DirectionalLight()
        environment.set(ColorAttribute(ColorAttribute.AmbientLight,ambientLightColor))
        directionalLight.color.set(directionalLightColor)
        directionalLight.direction.set(camera.direction)
        environment.add(directionalLight)

        //TEXTURES
        atlas = TextureAtlas(Gdx.files.internal("atlas/animation.atlas"))

        //ENGINE
        engine = Engine()
      
        //MANAGERS

          //ANIMATION
          val animationSystem = AnimationSystem(
            atlas,
            params.creatures,
            camera.lookAt
          )
          engine.addSystem(animationSystem)

          //RENDERER
          groupStrategy = CameraGroupStrategy(camera)
          decalBatch = DecalBatch(groupStrategy)
          modelBatch = ModelBatch()
          engine.addSystem(Renderer(camera,viewport,modelBatch,decalBatch,background,environment))

          //CONTROLLER
          engine.addSystem(Controller())

        //FACTORIES

          //ENTITYFACTORY
          entityFactory = EntityFactory(animationSystem)

        //TEST
        //engine.addEntity(entityFactory.cube())
        engine.addEntity(entityFactory.player())

        viewport.update(Gdx.graphics.width, Gdx.graphics.height)
    }

    override fun dispose() {
        modelBatch.dispose()
        decalBatch.dispose()
        groupStrategy.dispose()
        atlas.dispose()
    }
}