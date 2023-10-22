package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.signals.Signal

class State(
  private val actions: Map<Value,()=>Unit>
): Component {
 enum class Value {
  STAND(10),WALK(10),FALL(10),
  USE(0),ATTACK(0)

  val animationorder: Int
 }

 private val current = TreeSet<Value>(
   k1,k2 =>
   return {
     k1.animationorder - k2.animationorder
   }
 )

 init {
   Value.values.forEach { s =>
     if actions[s] == null
       actions.put(s,{})
   }
   current.add(Value.STAND)
 }

 fun start(s: Value) {
   when(s) {
     STAND => {}
     WALK => {
       if current.contains(Value.FALL)
         return
       current.remove(Value.STAND)
       current.remove(Value.USE)
       current.add(s)
     }
     FALL => {
       current.remove(Value.STAND)
       current.remove(Value.WALK)
       current.remove(Value.USE)
       current.add(s)
     }
     USE => {
       if current.contains(Value.STAND) {
         current.remove(Value.ATTACK)
         current.add(s)
       }
     }
     ATTACK => {
       current.remove(Value.USE)
       current.add(s)
     }
   }
 }

 fun end(s: Value) {
   when(s) {
     STAND => {}
     WALK => {
       current.remove(s)
       current.add(Value.STAND)
     }
     FALL => {
       current.remove(s)
       current.add(Value.STAND)
     }
     USE => {
       current.remove(s)
     }
     ATTACK => {
       current.remove(s)
     }
   }
 }

 fun act() {
    current.forEach { s=>
      actions[s]()
    }
 }

 fun animation(): Value = current.first()
}
