package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import ru.ltow.bb.component.Player

class Controller: EntitySystem() {
  val standers: ImmutableArray<Entity>
  val walkers: ImmutableArray<Entity>

  val DRAG_SENSIVITY = 10f

  override fun addedToEngine(e: Engine) {
    standers = e.getEntitiesFor(Family.all(Player::class.java,Stand::class.java).get())
    walkers = e.getEntitiesFor(Family.all(Player::class.java,Walk::class.java).get())
    super()
  }

  override fun update(dt: Float) {
    standers.forEach() { e ->
      if(GDX.input.touchDragged() && drag.length > DRAG_SENSIVITY)
        e.add(Walk())
    }

    walkers.forEach() { e ->
      if(GDX.input.touchUp())
        e.add(Stand())
      else
        Mappers.walk.get(e).vector = drag.vector
    }
  }
}
