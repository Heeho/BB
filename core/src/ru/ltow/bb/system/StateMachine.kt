package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.gdx.math.Vector2

class StateMachine(
  stateChanged: Listener
): EntitySystem {
 override fun update(delta: Float) {
  stateChanged.forEach {
   val flags it.state.getflags()
    it.state.current.(add,remove) = f(flags)
    state.getstates().forEach {
     it.run()
    }
   }
  }
    companion object {
        fun calculateFace(v: Vector2): Face {
            return if (v.x >= 0 && v.y >= 0) Face.NE
            else if (v.x >= 0 && v.y < 0) Face.SE
            else if (v.x < 0 && v.y >= 0) Face.NW
            else Face.SW
        }
    }
}
