package ru.ltow.bb.screen.prefs

import com.badlogic.gdx.Gdx
import ru.ltow.bb.screen.BaseScreen
import ru.ltow.bb.Core

class Screen(
    core: Core,
    ui: UI = UI(core.skin)
): BaseScreen(core,ui) {
    private val prefs = Gdx.app.getPreferences("prefs")
    private var delta = 0f

    override fun render(delta: Float) {
        //world.rotateCameraAroundY(invert = false, auto = true)
        super.render(delta)
    }
}