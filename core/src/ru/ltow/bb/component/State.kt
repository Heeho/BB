package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import java.util.*

class State(
  private val billboard: Billboard,
  private val motion: Motion
): Component {
  enum class Action(val animationOrder: Int) {
    STAND(10),
    WALK(10) {
      override fun act(s: State) {
        s.billboard.translate(s.motion.velocity)
      }
    },
    FALL(10),
    USE(0),
    ATTACK(0)

    abstract fun act(s: State)
  }

  enum class Face {
    SW,NW,SE,NE

    fun get(v: Vector2): Face {
      return
      if (v.x >= 0 && v.y >= 0) Face.NE
      else if (v.x >= 0 && v.y < 0) Face.SE
      else if (v.x < 0 && v.y >= 0) Face.NW
      else Face.SW
    }
  }

  private val current = TreeSet<Action>() {
    k1, k2 ->
    k1.animationOrder - k2.animationOrder
  }

  init {
    current.add(Action.STAND)
  }

  fun start(a: Action) {
    when(a) {
      Action.STAND -> {}
      Action.WALK -> {
        if(!current.contains(Action.FALL)) {
          if(current.add(a)) {
            current.remove(Action.STAND)
            current.remove(Action.USE)
          }
        }
      }
      Action.FALL -> {
        if(current.add(a)) {
          current.remove(Action.STAND)
          current.remove(Action.WALK)
          current.remove(Action.USE)
        }   
      }
      Action.USE -> {
        if(current.contains(Action.STAND)) {
          current.remove(Action.ATTACK)
          current.add(a)
        }
      }
      Action.ATTACK -> {
        current.remove(Action.USE)
        current.add(a)
      }
    }
  }

  fun end(a: Action) {
    when(a) {
      Action.STAND -> {}
      Action.WALK -> {
        current.remove(a)
        current.add(Action.STAND)
      }
      Action.FALL -> {
        current.remove(a)
        current.add(Action.STAND)
      }
      Action.USE -> {
        current.remove(a)
      }
      Action.ATTACK -> {
        current.remove(a)
      }
    }
  }

  fun animation(): Action = current.first()

  fun states(): List<Action> = current.toList()
}
