package ru.ltow.bb.client.screen.menu

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.ScreenAdapter
import ru.ltow.bb.client.Core
import ru.ltow.bb.client.screen.game.Screen

class Screen(
    val core: Core
): ScreenAdapter() {
    private val ui: UI = UI(core.skin)

    init {
        Gdx.input.inputProcessor = object: InputAdapter() {
            override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                core.setScreen(Screen(core))
                dispose()
                return true
            }
        }
    }

    override fun render(delta: Float) {
        ui.viewport.apply()
        ui.act(delta)
        ui.draw()
    }

    override fun resize(width: Int, height: Int) {
        ui.viewport.update(width,height,true)
        ui.viewport.apply()
    }

    override fun dispose() {
        ui.dispose()
    }
}