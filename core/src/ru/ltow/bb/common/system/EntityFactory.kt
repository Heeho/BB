package ru.ltow.bb.common.system

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Vector3
import ru.ltow.bb.client.component.Model
import ru.ltow.bb.client.component.Player
import ru.ltow.bb.common.component.Creature

class EntityFactory(
) {
  fun cube(position: Vector3): Entity {
    return Entity().apply {
      add(
        Model(
          ModelBuilder().createBox(
            1f, 1f, 1f,
            Material(ColorAttribute.createDiffuse(Color.BLUE), BlendingAttribute()),
            VertexAttributes.Usage.Position.toLong() or VertexAttributes.Usage.Normal.toLong()
          ),
          position
        )
      )
    }
  }

  fun player(): Entity = creature(Creature("toad")).add(Player())

  fun creature(c: Creature): Entity {
    //val h = Health(creature.hp)
    //...
    return Entity().add(c)
  }
}