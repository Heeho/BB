package ru.ltow.bb.client

import com.badlogic.gdx.graphics.PerspectiveCamera

class Camera (
    viewportSize: Float,
    fieldOfView: Float,
    near: Float,
    far: Float
    //val rotationSensivity: Float,
): PerspectiveCamera(fieldOfView,viewportSize,viewportSize) {
    //private val prefs: Preferences = Gdx.app.getPreferences("prefs")
    //private val invertX = prefs.getBoolean("CONTROLS_INVERT_X",false)
    //private val invertY = prefs.getBoolean("CONTROLS_INVERT_Y",false)

    init {
        this.near = near
        this.far = far
        this.update()
    }

    //fun rotateAroundLookAtY(auto: Boolean = false, invert: Boolean = invertX) = rotateAround(playerPosition,Vector3(0f,1f,0f),    rotationSensivity * (if(invert) 1f else -1f) * (if(auto) 1f else Gdx.input.deltaX.toFloat()))
    //fun rotateAroundLookAtX(auto: Boolean = false, invert: Boolean = invertY) = rotateAround(playerPosition,direction.cpy().crs(up).nor(),rotationSensivity * (if(invert) 1f else -1f) * (if(auto) 1f else Gdx.input.deltaY.toFloat()))
    //fun pick(x: Int, y: Int) {}
}