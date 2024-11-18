package ru.ltow.bb.client.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class Terrain(
    val xy: Vector2
): Component {
    val neighbours: Array<Terrain?> = Array<Terrain?>(8) { _ -> null }

    companion object {
        val landscape = ArrayList<Terrain>()
        val edges = HashSet<Terrain>()
        private val TERRAINCELLSIZE = 10.0f

        val NW = Pair(7,Vector2(-TERRAINCELLSIZE, TERRAINCELLSIZE))
        val N = Pair(8,Vector2(0f, TERRAINCELLSIZE))
        val NE = Pair(9,Vector2(TERRAINCELLSIZE, TERRAINCELLSIZE))
        val E = Pair(6,Vector2(TERRAINCELLSIZE,0f))
        val SE = Pair(3,Vector2(TERRAINCELLSIZE,-TERRAINCELLSIZE))
        val S = Pair(2,Vector2(0f,-TERRAINCELLSIZE))
        val SW = Pair(1,Vector2(-TERRAINCELLSIZE,-TERRAINCELLSIZE))
        val W = Pair(4,Vector2(-TERRAINCELLSIZE,0f))
    }

    fun generate(direction: Pair<Int,Vector2>) {
        if(neighbours[direction.first] == null) return
        Terrain(xy).let {
            it.xy.add(direction.second)
            neighbours[direction.first] = it
            it.neighbours[reverse(direction).first] = this
            it.findneighbours()
            landscape.add(it)
            edges.add(it)
            if(!this.neighbours.contains(null)) edges.remove(this)
        }
    }

    private fun reverse(direction: Pair<Int,Vector2>): Pair<Int,Vector2> {
        return when(direction) {
            NW -> SE
            N -> S
            NE -> SW
            E -> W
            SE -> NW
            S -> N
            SW -> NE
            W -> E
            else -> direction
        }
    }
    fun findneighbours() {
        neighbours.forEach {
            //TODO добавляем в neighbours соседей, общих с источником
        }
    }

}