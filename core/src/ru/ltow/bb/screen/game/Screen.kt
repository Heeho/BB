package ru.ltow.bb.screen.game

import ru.ltow.bb.screen.BaseScreen
import ru.ltow.bb.Core
import ru.ltow.bb.World

class Screen(
    core: Core,
    ui: UI = UI(core.skin)
): BaseScreen(core,ui) {
    private val world = World()

    override fun render(delta: Float) {
        world.engine.update(delta)
        super.render(delta)
    }
    override fun resize(width: Int, height: Int) {
        world.viewport.update(width,height)
        super.resize(width, height)
    }

    override fun dispose() {
        world.dispose()
        super.dispose()
    }
}