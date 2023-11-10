package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.decals.Decal
import com.badlogic.gdx.math.Vector3
import ru.ltow.bb.Camera

class Billboard (
    x: Float = 0f,
    y: Float = 0f,
    z: Float = 0f,
    frame: TextureRegion
): Component {
    private val decal: Decal = Decal.newDecal(1f,1f,frame,true).apply {
        setPosition(x,y,z)
    }

    fun getBillboard(camera: Camera) = decal.apply { lookAt(camera.position,camera.up) }
    fun translate(v: Vector3) { decal.translate(v) }
}