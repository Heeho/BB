package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.signals.Signal

class State(val owner: Entity): Component {
  enum class Value { STAND,WALK,FALL }
  enum class Face { NW,SW,NE,SE }
  private val current: HashSet<Value> = hashSetOf(Value.STAND)
  //сортируем для анимации
  //{ s1,s2 =>
  //  s1.ordinal < s2.ordinal
  //}

  private val flags = HashMap<Value,Boolean>()

  init {
    Value.values().forEach { flags[it] = false }
  }

  fun setflag(
    key: Value,
    value: Boolean
  ) {
    if (flags.put(key,value) == value) return
    stateChanged.dispatch(owner)
  }

  fun getflags() = flags

  fun getanimation(): Value = current.first()

  fun getstates(): List<Value> = current.toList()

  companion object {
    val stateChanged = Signal<Entity>()
  }
}
