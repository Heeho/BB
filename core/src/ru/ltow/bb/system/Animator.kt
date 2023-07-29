package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import ru.ltow.bb.component.Animation
import ru.ltow.bb.component.Billboard
import ru.ltow.bb.component.Sprite

class Animator: EntitySystem() {
    private lateinit var animations: ImmutableArray<Entity>
    private lateinit var sprites: ImmutableArray<Entity>

    override fun addedToEngine(engine: Engine?) {
        if(engine != null) {
            animations = engine.getEntitiesFor(Family.all(Billboard::class.java, Animation::class.java).get())
            sprites = engine.getEntitiesFor(Family.all(Billboard::class.java, Sprite::class.java).get())
        }
        super.addedToEngine(engine)
    }

    override fun update(deltaTime: Float) {
        //TODO для анимаций и спрайтов выбрать кадры, исходя из значений состояния и направления. По умолчанию берем State.Action.STAND и Direction.Face.SOUTH
    }
}