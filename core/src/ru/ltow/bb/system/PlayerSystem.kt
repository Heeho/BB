package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.ashley.utils.ImmutableArray
import ru.ltow.bb.component.Billboard
import ru.ltow.bb.component.Player
import ru.ltow.bb.component.Walk

class PlayerSystem(
  i: Float
): IntervalSystem(i) {
  lateinit var billboards: ImmutableArray<Entity>
  lateinit var standing: ImmutableArray<Entity>
  lateinit var walking: ImmutableArray<Entity>

  override fun addedToEngine(e: Engine) {
    billboards = e.getEntitiesFor(Family.all(Player::class.java, Billboard::class.java).get())
    standing = e.getEntitiesFor(Family.all(Player::class.java).exclude(Walk::class.java).get())
    walking = e.getEntitiesFor(Family.all(Player::class.java,Walk::class.java).get())
  }

  override fun updateInterval() {
  }
}
