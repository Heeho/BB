package ru.ltow.bb.server.screen

import com.badlogic.gdx.ScreenAdapter
import ru.ltow.bb.client.Core
import ru.ltow.bb.client.World

class Screen(
    val core: Core
): ScreenAdapter() {
    private val ui: UI = UI()
    private val world = World()

    override fun render(delta: Float) {
        world.engine.update(delta)
        ui.viewport.apply()
        ui.act(delta)
        ui.draw()
    }

    override fun resize(width: Int, height: Int) {
        world.viewport.update(width,height)
        ui.viewport.update(width,height,true)
        ui.viewport.apply()
    }

    override fun dispose() {
        world.dispose()
        ui.dispose()
    }
}