package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import ru.ltow.bb.Camera
import ru.ltow.bb.component.Animations
import ru.ltow.bb.component.Model
import ru.ltow.bb.component.Billboard

class Renderer(
  private val camera: Camera,
  private val viewport: FitViewport,
  private val modelBatch: ModelBatch,
  private val decalBatch: DecalBatch,
  private val background: Color,
  private val environment: Environment
): EntitySystem() {
  val models: ImmutableArray<Entity>
  val billboards: ImmutableArray<Entity>

  override fun addedToEngine(e: Engine) {
    models = engine.getEntitiesFor(Family.all(Model::class.java).get())
    billboards = engine.getEntitiesFor(Family.all(Billboard::class.java).get())
    super()
  }
       
  override fun update(dt: Float) {
    viewport.apply()
    camera.update()

    Gdx.gl.glClearColor(background.r, background.g, background.b, background.a)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

    modelBatch.begin(camera)
    modelEntities.forEach {
      modelBatch.render(Mappers.model.get(it).instance,environment)
    }

    billboards.forEach {
      decalBatch.add(
        Mappers.billboard.get(it).get(camera)
      ) 
    }

    modelBatch.end()
    decalBatch.flush()

    super.update(deltaTime)
  }
}