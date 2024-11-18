package ru.ltow.bb.client.screen.prefs

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import ru.ltow.bb.client.Core

class Screen(
    val core: Core
): ScreenAdapter() {
    private val ui: UI = UI()

    private val prefs = Gdx.app.getPreferences("prefs")
    private var delta = 0f

    override fun render(delta: Float) {
        //world.rotateCameraAroundY(invert = false, auto = true)
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