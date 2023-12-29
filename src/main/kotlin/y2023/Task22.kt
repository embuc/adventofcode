package y2023

import Task
import utils.borrowed.Point2D
import utils.size

//  --- Day 22: Sand Slabs ---
// Another one that was too hard for me to solve in the given time-frame,
// this solution is from user: github.com/clouddJR.
// *** Needs more work ***.
class Task22(input: List<String>):Task {
	private val bricks = input.mapIndexed { index, line -> Brick.from(index, line) }.sortedBy { it.zRange.first }
	private val supports = mutableMapOf<Int, MutableSet<Int>>()
	private val supported = mutableMapOf<Int, MutableSet<Int>>()

	init {
		val maxes = mutableMapOf<Point2D, Pair<Int, Int>>().withDefault { -1 to 0 }

		for (brick in bricks) {
			val points = brick.points2D()
			val maxZ = points.map { maxes.getValue(it) }.maxOf { it.second }
			brick.zRange = maxZ + 1..<maxZ + 1 + brick.zRange.size()

			for (point in points) {
				val (id, z) = maxes.getValue(point)
				if (z == maxZ && id != -1) {
					supports.getOrPut(id) { mutableSetOf() }.add(brick.id)
					supported.getOrPut(brick.id) { mutableSetOf() }.add(id)
				}
				maxes[point] = brick.id to brick.zRange.last
			}
		}
	}

	override fun a(): Int {
		return bricks.size - supported.values.filter { it.size == 1 }.map { it.toSet() }.reduce(Set<Int>::union).size
	}

	override fun b(): Int {
		return bricks.sumOf { brick ->
			val falling = mutableSetOf(brick.id)

			var nextBricks: Set<Int> = supports.getOrDefault(brick.id, emptySet())
			while (nextBricks.isNotEmpty()) {
				nextBricks = buildSet {
					for (next in nextBricks) {
						if ((supported.getValue(next) - falling).isEmpty()) {
							falling += next
							addAll(supports.getOrDefault(next, emptySet()))
						}
					}
				}
			}

			falling.size - 1
		}
	}

	private data class Brick(val id: Int, val xRange: IntRange, val yRange: IntRange, var zRange: IntRange) {
		fun points2D() = xRange.flatMap { x -> yRange.map { y -> Point2D(x, y) } }

		companion object {
			fun from(index: Int, str: String): Brick {
				val (b1, b2) = str.split("~")
				val (x1, y1, z1) = b1.split(",").map { it.toInt() }
				val (x2, y2, z2) = b2.split(",").map { it.toInt() }
				return Brick(index, x1..x2, y1..y2, z1..z2)
			}
		}
	}
}