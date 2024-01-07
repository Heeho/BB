package ru.ltow.bb.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.ashley.utils.ImmutableArray
import ru.ltow.bb.Camera
import ru.ltow.bb.component.*
import ru.ltow.bb.component.Face as FaceC
import ru.ltow.bb.component.Animation as AnimationC

class FaceSystem(
    val camera: Camera,
    i: Float
): IntervalSystem(i) {
    enum class Face { SE,SW,NW,NE }

    lateinit var faces: ImmutableArray<Entity>

    override fun addedToEngine(e: Engine) {
        faces = e.getEntitiesFor(Family.all(FaceC::class.java, AnimationC::class.java, Billboard::class.java).get())

        e.addEntityListener(
            Family.all(FaceC::class.java,Walk::class.java).exclude(Attack::class.java).get(),
            object: EntityListener {
                override fun entityAdded(e: Entity) {
                    Mappers.face.get(e).vector = Mappers.walk.get(e).vector
                }
                override fun entityRemoved(e: Entity) {}
            }
        )
    }

    override fun updateInterval() {
        faces.forEach { e ->
            val dv = Mappers.billboard.get(e).decal.position.cpy().sub(camera.position)
            val fv = Mappers.face.get(e).vector.cpy()

            val dot = dv.cpy().dot(fv)
            val crs = dv.cpy().crs(fv).y

            Mappers.animation.get(e).setface(
                if(dot >= 0 && crs >= 0) Face.NW
                else if(dot >= 0 && crs < 0) Face.NE
                else if(dot < 0 && crs >= 0) Face.SW
                else Face.SE
            )
        }
    }
}