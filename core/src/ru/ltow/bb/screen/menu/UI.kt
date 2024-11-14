package ru.ltow.bb.screen.menu

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ru.ltow.bb.screen.Skin

class UI(
    skin: Skin
): Stage(ScreenViewport()) {
    val table = Table()

    init {
        table.setFillParent(true)
        table.setDebug(true)
        this.addActor(table)
        table.add(Label("Press anywhere to start!", skin.labelStyle))
    }
}