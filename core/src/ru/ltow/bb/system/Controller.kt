package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import ru.ltow.bb.component.State

class Controller(
  val onDrag: () => Unit
    //e.motion.vector.set(dragv)
): EntitySystem {
  //val e = (player)

  fun onDragStart() {
    //e.state.setflag(State.Value.WALK,true)
  }
  fun onDragStop() {
    //e.state.setflag(State.Value.WALK,false)
  }
}
