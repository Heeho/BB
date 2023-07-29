package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.decals.Decal

class Billboard (
    x: Float = 0f
    ,y: Float = 0f
    ,z: Float = 0f
    ,frame: TextureRegion
): Component {
    var delta = 0f
    val decal: Decal = Decal.newDecal(1f,1f,frame,true)

    init {
        decal.setPosition(x,y,z)
    }
}