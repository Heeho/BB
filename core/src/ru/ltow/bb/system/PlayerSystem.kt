package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector3
import ru.ltow.bb.Camera
import ru.ltow.bb.component.Player
import ru.ltow.bb.component.Stand
import ru.ltow.bb.component.Walk

class PlayerSystem(
  val c: Camera,
  i: Float
): IntervalSystem(i), InputProcessor {
  lateinit var standing: ImmutableArray<Entity>
  lateinit var walking: ImmutableArray<Entity>

  var dragged = false
  val walk = Walk(Vector3())

  override fun addedToEngine(e: Engine) {
    standing = e.getEntitiesFor(Family.all(Player::class.java, Stand::class.java).get())
    walking = e.getEntitiesFor(Family.all(Player::class.java, Walk::class.java).get())
  }

  override fun updateInterval() {
  }

  override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
    return if(button == Input.Buttons.RIGHT) {
      standing.forEach { it.add(walk) }
      true
    } else false
  }
  override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
    return if(button == Input.Buttons.RIGHT) {
      walking.forEach { it.remove(Walk::class.java) }
      true
    } else false
  }
  override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
    return if(dragged) {
      walk.vector.set(Gdx.input.deltaX.toFloat(),0f,Gdx.input.deltaY.toFloat())
      true
    } else false
  }

  override fun keyDown(p0: Int): Boolean {
    return false
  }

  override fun keyUp(p0: Int): Boolean {
    return false
  }

  override fun keyTyped(p0: Char): Boolean {
    return false
  }

  override fun mouseMoved(p0: Int, p1: Int): Boolean {
    return false
  }

  override fun scrolled(p0: Float, p1: Float): Boolean {
    return false
  }
}
