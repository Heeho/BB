package ru.ltow.bb

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.math.Vector3

class Camera (
    viewportSize: Float,
    fieldOfView: Float,
    near: Float,
    far: Float,
    position: Vector3,
    val lookAt: Vector3,
    val rotationSensivity: Float
): PerspectiveCamera(fieldOfView,viewportSize,viewportSize) {
    init {
        this.position.set(position)
        this.lookAt(lookAt)
        this.near = near
        this.far = far
        this.update()
    }

    fun rotateAroundY(invert: Boolean = false, auto: Boolean = false) = rotateAround(lookAt,up,                           rotationSensivity * (if(invert) 1f else -1f) * (if(auto) 1f else Gdx.input.deltaX.toFloat()))
    fun rotateAroundX(invert: Boolean = false, auto: Boolean = false) = rotateAround(lookAt,direction.cpy().crs(up).nor(),rotationSensivity * (if(invert) 1f else -1f) * (if(auto) 1f else Gdx.input.deltaY.toFloat()))
}