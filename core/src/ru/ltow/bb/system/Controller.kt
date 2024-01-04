package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3
import ru.ltow.bb.component.Player
import ru.ltow.bb.component.Stand
import ru.ltow.bb.component.Walk

class Controller: IntervalSystem(0.045f) {
  lateinit var standers: ImmutableArray<Entity>
  lateinit var walkers: ImmutableArray<Entity>

  val DRAG_SENSIVITY = 10f
  val DRAG_VECTOR_PLACEHOLDER = Vector3()
  val PLAYER_POINTER = 1

  /*val processor = object: InputAdapter() {
    private var drag = 0

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
      return true
    }
    override fun touchDragged(x: Int, y: Int, pointer: Int): Boolean {
      return true
    }
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
      return true
    }
  }*/

  override fun addedToEngine(e: Engine) {
    standers = e.getEntitiesFor(Family.all(Player::class.java, Stand::class.java).get())
    walkers = e.getEntitiesFor(Family.all(Player::class.java, Walk::class.java).get())
  }

  override fun updateInterval() {
    standers.forEach { e ->
      if(DRAG_VECTOR_PLACEHOLDER.len() > DRAG_SENSIVITY)
        e.add(Walk(DRAG_VECTOR_PLACEHOLDER))
    }
    walkers.forEach { e ->
      if(Gdx.input.isTouched)
        e.add(Stand())
    }
  }
}
