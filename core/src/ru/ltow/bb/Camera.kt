package ru.ltow.bb

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.math.Vector3
import kotlin.math.abs

class Camera (
    viewportSize: Float,
    fieldOfView: Float,
    near: Float,
    far: Float,
    position: Vector3,
    val lookAt: Vector3,
    val rotationSensivity: Float
): PerspectiveCamera(fieldOfView,viewportSize,viewportSize), InputProcessor {

    private val prefs: Preferences = Gdx.app.getPreferences("prefs")
    private val invertX = prefs.getBoolean("CONTROLS_INVERT_X",false)
    private val invertY = prefs.getBoolean("CONTROLS_INVERT_X",false)
    private val dragThreshold = prefs.getInteger("CONTROLS_DRAG_THRESHOLD",3)

    private var dragged = false
    private var drag = 0

    init {
        this.position.set(position)
        this.lookAt(lookAt)
        this.near = near
        this.far = far
        this.update()
    }

    fun rotateAroundLookAtY(invert: Boolean = false, auto: Boolean = false) = rotateAround(lookAt,Vector3(0f,1f,0f),    rotationSensivity * (if(invert) 1f else -1f) * (if(auto) 1f else Gdx.input.deltaX.toFloat()))
    fun rotateAroundLookAtX(invert: Boolean = false, auto: Boolean = false) = rotateAround(lookAt,direction.cpy().crs(up).nor(),rotationSensivity * (if(invert) 1f else -1f) * (if(auto) 1f else Gdx.input.deltaY.toFloat()))
    fun pick(x: Int, y: Int) {}

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return if(button == Input.Buttons.LEFT) {
            drag = 0
            dragged = true
            return true
        } else false
    }
    override fun touchDragged(x: Int, y: Int, pointer: Int): Boolean {
        return if (dragged) {
            rotateAroundLookAtY(invertY)
            rotateAroundLookAtX(invertX)
            drag += abs(Gdx.input.deltaX) + abs(Gdx.input.deltaY)
            true
        } else false
    }
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return if(button == Input.Buttons.LEFT) {
            if (drag <= dragThreshold) pick(screenX, screenY)
            dragged = false
            true
        } else false
    }
    override fun keyDown(p0: Int): Boolean {
        return false
    }

    override fun keyUp(p0: Int): Boolean {
        return false
    }

    override fun keyTyped(p0: Char): Boolean {
        return false
    }

    override fun mouseMoved(p0: Int, p1: Int): Boolean {
        return false
    }

    override fun scrolled(p0: Float, p1: Float): Boolean {
        return false
    }
}