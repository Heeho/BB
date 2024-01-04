package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3
import ru.ltow.bb.Mappers
import ru.ltow.bb.component.Player
import ru.ltow.bb.component.Stand
import ru.ltow.bb.component.Walk

class Controller: EntitySystem() {
  lateinit var standers: ImmutableArray<Entity>
  lateinit var walkers: ImmutableArray<Entity>

  val DRAG_SENSIVITY = 10f

  override fun addedToEngine(e: Engine) {
    standers = e.getEntitiesFor(Family.all(Player::class.java, Stand::class.java).get())
    walkers = e.getEntitiesFor(Family.all(Player::class.java, Walk::class.java).get())
  }

  override fun update(dt: Float) {
    standers.forEach() { e ->
      //if(Gdx.input.de && drag.length > DRAG_SENSIVITY)
        e.add(Walk())
    }

    walkers.forEach() { e ->
      if(Gdx.input.isTouched)
        e.add(Stand())
      else
        Mappers.walk.get(e).vector = Vector3() //drag.vector
    }
  }
}
