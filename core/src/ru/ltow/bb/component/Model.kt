package ru.ltow.bb.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3

class Model (
    model: Model,
    position: Vector3 = Vector3()
): Component {
    val instance = ModelInstance(model,Matrix4().setToTranslation(position))
}
