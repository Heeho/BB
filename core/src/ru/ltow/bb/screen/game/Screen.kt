package ru.ltow.bb.screen.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import ru.ltow.bb.BaseScreen
import ru.ltow.bb.Core
import ru.ltow.bb.World

class Screen(
    core: Core,
    private val ui: UI = UI(core.skin)
): BaseScreen(core,ui) {
    private val world = World()

    private val prefs: Preferences = Gdx.app.getPreferences("prefs")
    private val invertX = prefs.getBoolean("CONTROLS_INVERT_X",false)
    private val invertY = prefs.getBoolean("CONTROLS_INVERT_X",false)
    private val dragThreshold = prefs.getInteger("CONTROLS_DRAG_THRESHOLD",3)

    private val controller = Controller(
        dragThreshold,
        {
            world.camera.rotateAroundX(invertY)
            world.camera.rotateAroundY(invertX)
        },
        { screenX,screenY ->  },
        {  }
    )

    init {
        Gdx.input.inputProcessor = controller
    }

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