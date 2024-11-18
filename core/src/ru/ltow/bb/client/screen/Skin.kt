package ru.ltow.bb.client.screen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin

class Skin: Skin() {
    val labelStyle = Label.LabelStyle()

    init {
        add("white", Color.WHITE)
        add("font",  BitmapFont())

        labelStyle.font = getFont("font")
        labelStyle.fontColor = getColor("white")
    }
}