package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.decals.Decal
import com.badlogic.gdx.math.Vector3
import ru.ltow.bb.Camera

class Billboard (
  position: Vector3,
  frame: TextureRegion,
  size: Float = 1f
): Component {
  val decal: Decal = Decal.newDecal(size,size,frame,true).apply { setPosition(position) }

  fun get(c: Camera) = decal.apply { lookAt(c.position,c.up) }
  fun translate(v: Vector3) { decal.translate(v) }
}
