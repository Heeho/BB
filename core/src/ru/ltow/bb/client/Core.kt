package ru.ltow.bb.client

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import ru.ltow.bb.client.screen.Skin
import ru.ltow.bb.client.screen.menu.Screen

class Core: Game() {
    lateinit var skin: Skin; private set

    private fun start() {
        this.setScreen(Screen(this))
    }

    override fun create() {
        skin = Skin()
        start()
    }

    override fun render() {
        screen.render(Gdx.graphics.deltaTime)
        super.render()
    }

    override fun resize(width: Int, height: Int) {
        screen.resize(width,height)
    }

    override fun dispose() {
        skin.dispose()
        screen.dispose()
        super.dispose()
    }
}