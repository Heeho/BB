package ru.ltow.bb.client.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.FitViewport
import ru.ltow.bb.client.Camera
import ru.ltow.bb.client.component.Model
import ru.ltow.bb.client.component.Billboard
import ru.ltow.bb.common.component.Mappers
import ru.ltow.bb.client.component.Player

class Renderer(
  private val camera: Camera,
  private val viewport: FitViewport,
  private val modelBatch: ModelBatch,
  private val decalBatch: DecalBatch,
  private val background: Color,
  private val environment: Environment
): EntitySystem() {
  lateinit var models: ImmutableArray<Entity>
  lateinit var billboards: ImmutableArray<Entity>
  lateinit var playerbillboards: ImmutableArray<Entity>

  val playerPosition = Vector3()
  val cameraPosition = Vector3()
  val cameraShift = Vector3(0f, 5f, 10f)

  override fun addedToEngine(e: Engine) {
    models = engine.getEntitiesFor(Family.all(Model::class.java).get())
    billboards = engine.getEntitiesFor(Family.all(Billboard::class.java).get())
    playerbillboards = engine.getEntitiesFor(Family.all(Player::class.java, Billboard::class.java).get())
  }
       
  override fun update(dt: Float) {
    viewport.apply()

    playerPosition.setZero()
    playerbillboards.forEach { playerPosition.add(Mappers.billboard.get(it).decal.position) }
    playerPosition.scl(1f / playerbillboards.size().toFloat())
    cameraPosition.setZero().add(playerPosition).add(cameraShift)
    camera.position.set(cameraPosition)
    camera.lookAt(playerPosition)
    camera.update()

    Gdx.gl.glClearColor(background.r, background.g, background.b, background.a)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

    modelBatch.begin(camera)
    models.forEach {
      modelBatch.render(Mappers.model.get(it).instance,environment)
    }

    billboards.forEach {
      decalBatch.add(
        Mappers.billboard.get(it).get(camera)
      ) 
    }

    modelBatch.end()
    decalBatch.flush()

    super.update(dt)
  }
}