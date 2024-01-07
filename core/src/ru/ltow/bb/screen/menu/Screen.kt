package ru.ltow.bb.screen.menu

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import ru.ltow.bb.screen.BaseScreen
import ru.ltow.bb.Core
import ru.ltow.bb.screen.game.Screen

class Screen(
    core: Core,
    ui: UI = UI(core.skin)
): BaseScreen(core,ui) {
    init {
        Gdx.input.inputProcessor = object: InputAdapter() {
            override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                core.setScreen(Screen(core))
                dispose()
                return super.touchDown(screenX, screenY, pointer, button)
            }
        }
    }

    override fun render(delta: Float) {
        super.render(delta)
    }
}