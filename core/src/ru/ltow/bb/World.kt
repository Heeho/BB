package ru.ltow.bb

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
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
import ru.ltow.bb.component.Creature
import com.badlogic.gdx.utils.Array
import ru.ltow.bb.system.*

class World(
  creatures: Array<Creature> = Array(arrayOf(Creature("toad")))
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

    init {
        //RENDERER

          //CAMERA
          val viewportSize = 10f
          camera = Camera(
              viewportSize, 66f, 1f, 10000f,
              Vector3(0f, 5f, 10f),
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


        //FACTORIES

            //ENTITYFACTORY
            entityFactory = EntityFactory()

        //ENGINE
        engine = Engine()

            //SYSTEMS
                val ITERATING_SYSTEM_INTERVAL = 0.05f

                //ANIMATION
                engine.addSystem(AnimationSystem(atlas,creatures,ITERATING_SYSTEM_INTERVAL))
                engine.addSystem(FaceSystem(camera,ITERATING_SYSTEM_INTERVAL))

                //ACTIONS
                engine.addSystem(WalkSystem(ITERATING_SYSTEM_INTERVAL))

                //RENDERER
                groupStrategy = CameraGroupStrategy(camera)
                decalBatch = DecalBatch(groupStrategy)
                modelBatch = ModelBatch()
                engine.addSystem(Renderer(camera,viewport,modelBatch,decalBatch,background,environment))

                //PLAYER
                val player = PlayerSystem(camera,ITERATING_SYSTEM_INTERVAL)
                engine.addSystem(player)

            //CONTROLLER
            Gdx.input.inputProcessor = InputMultiplexer(camera,player)

        //TEST
        engine.addEntity(entityFactory.cube(Vector3(1f,0f,0f)))
        engine.addEntity(entityFactory.cube(Vector3(1f,1f,0f)))
        engine.addEntity(entityFactory.player())
        engine.addEntity(entityFactory.creature(Creature("toad",Vector3(3f,0f,0f))))
        engine.addEntity(entityFactory.creature(Creature("toad",Vector3(2f,-2f,2f))))

        viewport.update(Gdx.graphics.width, Gdx.graphics.height)
    }

    override fun dispose() {
        modelBatch.dispose()
        decalBatch.dispose()
        groupStrategy.dispose()
        atlas.dispose()
    }
}