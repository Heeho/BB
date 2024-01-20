package ru.ltow.bb.component

import com.badlogic.ashley.core.ComponentMapper

class Mappers {
  companion object {
    val animation: ComponentMapper<Animation> = ComponentMapper.getFor(Animation::class.java)
    val attack: ComponentMapper<Attack> = ComponentMapper.getFor(Attack::class.java)
    val billboard: ComponentMapper<Billboard> = ComponentMapper.getFor(Billboard::class.java)
    val creature: ComponentMapper<Creature> = ComponentMapper.getFor(Creature::class.java)
    val face: ComponentMapper<Face> = ComponentMapper.getFor(Face::class.java)
    val grounded: ComponentMapper<Grounded> = ComponentMapper.getFor(Grounded::class.java)
    val model: ComponentMapper<Model> = ComponentMapper.getFor(Model::class.java)
    val player: ComponentMapper<Player> = ComponentMapper.getFor(Player::class.java)
    val use: ComponentMapper<Use> = ComponentMapper.getFor(Use::class.java)
    val walk: ComponentMapper<Walk> = ComponentMapper.getFor(Walk::class.java)
  }
}
