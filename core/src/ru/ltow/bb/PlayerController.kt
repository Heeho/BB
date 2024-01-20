package ru.ltow.bb

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter

class PlayerController(
    val onTouchDownLEFTorP0: () -> Unit,
    val onTouchDownRIGHTorP1: () -> Unit,
    val onTouchUpLEFTorP0: () -> Unit,
    val onTouchUpRIGHTorP1: () -> Unit,
    val onTouchDraggedP0: () -> Unit,
    val onTouchDraggedP1: () -> Unit
): InputAdapter() {
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return if (button == Input.Buttons.RIGHT || pointer == 1) {
            onTouchDownRIGHTorP1()
            true
        } else if (button == Input.Buttons.LEFT || pointer == 0) {
            onTouchDownLEFTorP0()
            true
        } else false
    }
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return if (button == Input.Buttons.RIGHT || pointer == 1) {
            onTouchUpRIGHTorP1()
            true
        } else if (button == Input.Buttons.LEFT || pointer == 0) {
            onTouchUpLEFTorP0()
            true
        } else false
    }
    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return when(pointer) {
            0 -> {
                onTouchDraggedP0()
                true
            }
            1 -> {
                onTouchDraggedP1()
                true
            }
            else -> false
        }
    }
}