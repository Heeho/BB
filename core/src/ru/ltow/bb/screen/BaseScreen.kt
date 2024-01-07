package ru.ltow.bb.screen

import com.badlogic.gdx.ScreenAdapter
import ru.ltow.bb.Core

open class BaseScreen (
    val core: Core,
    private val ui: BaseUI
): ScreenAdapter() {

    init {
        //Gdx.input.setCursorCatched(true)
    }

    override fun render(delta: Float) {
        ui.viewport.apply()
        ui.act(delta)
        ui.draw()
    }

    override fun dispose() {
        ui.dispose()
    }

    override fun resize(width: Int, height: Int) {
        ui.viewport.update(width,height,true)
        ui.viewport.apply()
    }
}