package ru.ltow.bb.screen.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import kotlin.math.abs

class Controller(
    val dragThreshold: Int,
    val rotateCameraAroundXY: () -> Unit,
    val pick: (Int, Int) -> Unit,
    val renderPicker: () -> Unit
): InputAdapter() {
    private var drag = 0

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        drag = 0
        return true
    }
    override fun touchDragged(x: Int, y: Int, pointer: Int): Boolean {
        rotateCameraAroundXY()
        drag += abs(Gdx.input.deltaX) + abs(Gdx.input.deltaY)
        return true
    }
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if(drag <= dragThreshold) {
            pick(screenX,screenY)
        } else {
            renderPicker()
        }
        return true
    }
}