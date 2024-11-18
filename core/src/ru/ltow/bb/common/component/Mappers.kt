package ru.ltow.bb.common.component

import com.badlogic.ashley.core.ComponentMapper
import ru.ltow.bb.client.component.Animation
import ru.ltow.bb.client.component.Billboard
import ru.ltow.bb.client.component.Model
import ru.ltow.bb.client.component.Player

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
