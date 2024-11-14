package ru.ltow.bb.screen.game

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport

class UI: Stage(ScreenViewport()) {
    val table = Table()

    init {
        table.setFillParent(true)
        table.setDebug(true)
        this.addActor(table)
    }
}