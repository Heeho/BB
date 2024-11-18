package ru.ltow.bb.client

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
import ru.ltow.bb.common.component.Creature
import com.badlogic.gdx.utils.Array
import ru.ltow.bb.client.system.AnimationSystem
import ru.ltow.bb.client.system.Renderer
import ru.ltow.bb.common.component.Mappers
import ru.ltow.bb.common.component.Walk
import ru.ltow.bb.common.system.*

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
        //CAMERA
        val viewportSize = 10f
        camera = Camera(
            viewportSize, 66f, 1f, 10000f
        )
        viewport = FitViewport(viewportSize, viewportSize, camera)

        //TEXTURES
        atlas = TextureAtlas(Gdx.files.internal("atlas/animation.atlas"))

        //FACTORIES

        //ENTITYFACTORY
        entityFactory = EntityFactory()

        //ENGINE
        engine = Engine()

        //SYSTEMS
            val ANIMATION_SYSTEM_INTERVAL = 0.05f
            val WALK_SYSTEM_INTERVAL = 0.015f
            val GRAVITY_SYSTEM_INTERVAL = 0.05f
            val PLAYER_SYSTEM_INTERVAL = 1f

            //PLAYER
            val player = PlayerSystem(PLAYER_SYSTEM_INTERVAL)
            engine.addSystem(player)

            //ACTIONS
            engine.addSystem(WalkSystem(WALK_SYSTEM_INTERVAL))

            //GRAVITY
            engine.addSystem(GravitySystem(GRAVITY_SYSTEM_INTERVAL))

            //ANIMATION
            engine.addSystem(AnimationSystem(atlas,creatures,ANIMATION_SYSTEM_INTERVAL))

            //FACE
            engine.addSystem(FaceSystem(camera,ANIMATION_SYSTEM_INTERVAL))

        //VIEW
            //ENVIRONMENT
            val directionalLight = DirectionalLight()
            environment.set(ColorAttribute(ColorAttribute.AmbientLight,ambientLightColor))
            directionalLight.color.set(directionalLightColor)
            directionalLight.direction.set(camera.direction)
            environment.add(directionalLight)

            //RENDERER
            groupStrategy = CameraGroupStrategy(camera)
            decalBatch = DecalBatch(groupStrategy)
            modelBatch = ModelBatch()
            engine.addSystem(
                Renderer(
                camera,
                viewport,
                modelBatch,
                decalBatch,
                background,
                environment
            )
            )

        //CONTROLLER
        Gdx.input.inputProcessor = PlayerController(
            onTouchDownLEFTorP0 = {},
            onTouchDownRIGHTorP1 = {
                player.standing.forEach { it.add(Walk()) }
            },
            onTouchUpLEFTorP0 = {},
            onTouchUpRIGHTorP1 = {
                player.walking.forEach { it.remove(Walk::class.java) }
            },
            onTouchDraggedP0 = {
                //camera.rotateAroundLookAtX()
                //camera.rotateAroundLookAtY()
                player.walking.forEach { Mappers.walk.get(it).vector.set(Gdx.input.deltaX.toFloat(),0f,Gdx.input.deltaY.toFloat()).nor() }
            },
            onTouchDraggedP1 = {}
        )

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