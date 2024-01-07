package ru.ltow.bb.screen.menu

import com.badlogic.gdx.scenes.scene2d.ui.Label
import ru.ltow.bb.screen.BaseUI
import ru.ltow.bb.screen.Skin

class UI(
    skin: Skin
): BaseUI() {
    init {
        table.add(Label("Press anywhere to start!", skin.labelStyle))
    }
}