// @author embuc
package utils

import kotlin.math.abs

open class Tile(var x: Long, var y: Long, val value: Char, var label: String) {
	override fun toString(): String {
		return value.toString()
	}
}

fun manhattanDistanceLong(p1: Tile, p2: Tile): Long {
	val deltaX = abs(p1.x - p2.x).toLong()
	val deltaY = abs(p1.y - p2.y).toLong()
	return deltaX + deltaY
}
