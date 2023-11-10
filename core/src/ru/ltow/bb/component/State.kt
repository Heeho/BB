package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import java.util.*

class State(
  private val billboard: Billboard,
  private val motion: Motion
): Component {
  enum class Value(val animationorder: Int) {
    STAND(10),
    WALK(10),
    FALL(10),
    USE(0),
    ATTACK(0)
  }

  enum class Face {
    SW,NW,SE,NE
  }

  private val current = TreeSet<Value>() {
    k1, k2 ->
    k1.animationorder - k2.animationorder
  }

  init {
    current.add(Value.STAND)
  }

  fun start(s: Value) {
    when(s) {
      Value.STAND -> {}
      Value.WALK -> {
        if(current.contains(Value.FALL)) return
        current.remove(Value.STAND)
        current.remove(Value.USE)
        current.add(s)
      }
      Value.FALL -> {
        current.remove(Value.STAND)
        current.remove(Value.WALK)
        current.remove(Value.USE)
        current.add(s)
      }
      Value.USE -> {
        if(current.contains(Value.STAND)) {
          current.remove(Value.ATTACK)
          current.add(s)
        }
      }
      Value.ATTACK -> {
        current.remove(Value.USE)
        current.add(s)
      }
    }
  }

  fun end(s: Value) {
    when(s) {
      Value.STAND -> {}
      Value.WALK -> {
        current.remove(s)
        current.add(Value.STAND)
      }
      Value.FALL -> {
        current.remove(s)
        current.add(Value.STAND)
      }
      Value.USE -> {
        current.remove(s)
      }
      Value.ATTACK -> {
        current.remove(s)
      }
    }
  }

  fun act() {
    current.forEach { s ->
      when(s) {
        Value.STAND -> {}
        Value.WALK -> { walk() }
        Value.FALL -> {}
        Value.USE -> {}
        Value.ATTACK -> {}
      }
    }
  }

  private fun walk() {
    billboard.translate(motion.velocity())
  }

  fun getAnimation(): Value = current.first()
  fun getStates(): List<Value> = current.toList()
}
