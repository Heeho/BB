package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import java.util.*

class State(
  private val billboard: Billboard,
  private val motion: Motion
): Component {
  enum class Action(val animationOrder: Int) {
    STAND(10) {
      override fun act(s: State) {
        //NONE
      }
    },
    WALK(10) {
      override fun act(s: State) {
        s.billboard.translate(s.motion.velocity())
      }
    },
    FALL(10) {
      override fun act(s: State) {
        //NONE
      }
    },
    USE(0) {
      override fun act(s: State) {
        //NONE
      }
    },
    ATTACK(0) {
      override fun act(s: State) {
        //NONE
      }
    };

    abstract fun act(s: State)
  }

  enum class Face {
    SW,NW,SE,NE;

    fun get(v: Vector2): Face {
      return if (v.x >= 0 && v.y >= 0) NE
      else if (v.x >= 0 && v.y < 0) SE
      else if (v.x < 0 && v.y >= 0) NW
      else SW
    }
  }

  private val actions = TreeSet<Action>() {
    k1, k2 ->
    k1.animationOrder - k2.animationOrder
  }

  init {
    actions.add(Action.STAND)
  }

  fun start(a: Action) {
    when(a) {
      Action.STAND -> {}
      Action.WALK -> {
        if(!actions.contains(Action.FALL)) {
          if(actions.add(a)) {
            actions.remove(Action.STAND)
            actions.remove(Action.USE)
          }
        }
      }
      Action.FALL -> {
        if(actions.add(a)) {
          actions.remove(Action.STAND)
          actions.remove(Action.WALK)
          actions.remove(Action.USE)
        }   
      }
      Action.USE -> {
        if(actions.contains(Action.STAND)) {
          actions.remove(Action.ATTACK)
          actions.add(a)
        }
      }
      Action.ATTACK -> {
        actions.remove(Action.USE)
        actions.add(a)
      }
    }
  }

  fun end(a: Action) {
    when(a) {
      Action.STAND -> {}
      Action.WALK -> {
        actions.remove(a)
        actions.add(Action.STAND)
      }
      Action.FALL -> {
        actions.remove(a)
        actions.add(Action.STAND)
      }
      Action.USE -> {
        actions.remove(a)
      }
      Action.ATTACK -> {
        actions.remove(a)
      }
    }
  }

  fun animation(): Action = actions.first()

  fun actions(): List<Action> = actions.toList()
}
