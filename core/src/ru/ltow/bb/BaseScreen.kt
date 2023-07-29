package ru.ltow.bb

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter

open class BaseScreen (
    val core: Core,
    private val ui: BaseUI
): ScreenAdapter() {

    init {
        //Gdx.input.setCursorCatched(true)
    }

    override fun render(delta: Float) {
        ui.act(delta)
        ui.viewport.apply()
        ui.draw()
    }

    override fun dispose() {
        ui.dispose()
    }

    override fun resize(width: Int, height: Int) {
        ui.viewport.update(width,height,true)
    }
}