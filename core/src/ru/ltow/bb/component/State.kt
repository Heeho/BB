package ru.ltow.bb.component

import com.badlogic.ashley.core.Component

class State(val owner: Entity): Component {
  enum class Value { STAND,WALK,FALL }
  private val current = setOf(Value.STAND)
  //сортируем для анимации
  { s1,s2 =>
    s1.ordinal < s2.ordinal
  }

  private val flags =
    HashMap<Value,Boolean>

  init {
    Value.values.forEach { flags.add(it,false) }
  }

  fun setflag(
    key: Value,
    value: Boolean
  ) {
    if flags.put(key,value) == value
      return
    stateChanged.dispatch(owner)
  }

  fun getflags(): Map = flags

  fun getanimation(): Value = current.first

  fun getstates(): List = current.values

  companion object {
    val stateChanged = Signal<Entity>()
  }
}
