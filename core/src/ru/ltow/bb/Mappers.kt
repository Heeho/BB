package ru.ltow.bb

class Mappers {
  companion object {
    val billboard = ComponentMapper.getFor(Billboard::class.java)
    val animation = ComponentMapper.getFor(Animation::class.java)
    val face = ComponentMapper.getFor(Face::class.java)

    val model = ComponentMapper.getFor(Model::class.java)

    val player = ComponentMapper.getFor(Player::class.java)

    val stand = ComponentMapper.getFor(Stand::class.java) 
    val walk = ComponentMapper.getFor(Walk::class.java)
    val fall = ComponentMapper.getFor(Fall::class.java)
    val use = ComponentMapper.getFor(Use::class.java)
    val attack = ComponentMapper.getFor(Attack::class.java)
  }
}
