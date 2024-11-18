package ru.ltow.bb.client.screen

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport

open class BaseUI: Stage(ScreenViewport()) {
    val table = Table()

    init {
        table.setFillParent(true)
        table.setDebug(true)
        this.addActor(table)
    }
}