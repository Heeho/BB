package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Matrix4

class Model (
    model: Model,
    x: Float = 0f,
    y: Float = 0f,
    z: Float = 0f
): Component {
    val modelInstance = ModelInstance(model, Matrix4().setToTranslation(x,y,z))
}