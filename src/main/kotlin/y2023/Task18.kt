package y2023

import Task
import utils.readInputAsListOfStrings
import java.util.*

object Task18 : Task {

	enum class Direction {
		UP, DOWN, LEFT, RIGHT;

		companion object {
			fun fromString(value: String): Direction {
				return when (value.lowercase(Locale.getDefault())) {
					"u" -> UP
					"d" -> DOWN
					"l" -> LEFT
					"r" -> RIGHT
					"3" -> UP
					"1" -> DOWN
					"2" -> LEFT
					"0" -> RIGHT
					else -> throw IllegalArgumentException("Unknown direction: $value")
				}
			}
		}
	}

	data class Instruction(val direction: String, val steps: Long)

	override fun a(): Any {
		val lines = readInputAsListOfStrings("2023_18.txt")
		val polygonInstructions = parseToPolygonInstructions(lines, false)
		val createPolygon = createPolygon(polygonInstructions)
		return lavaCubicMeters(createPolygon)
	}

	override fun b(): Any {
		val lines = readInputAsListOfStrings("2023_18.txt")
		val polygonInstructions = parseToPolygonInstructions(lines, true)
		val createPolygon = createPolygon(polygonInstructions)
		return lavaCubicMeters(createPolygon)

	}

	/*	Shoelace formula
	*  Area = |(1/2) * Σ(from i=1 to n-1) of (x_i * y_(i+1) - x_(i+1) * y_i) + (x_n * y_1 - x_1 * y_n)|
	*  */
	fun calculateArea(vertices: List<Pair<Long, Long>>): Long {
		var area = 0L
		val n = vertices.size

		for (i in 0 until n ) {
			val j = (i + 1) % n
			area += vertices[i].first * vertices[j].second
			area -= vertices[j].first * vertices[i].second
		}

		return Math.abs(area / 2L)
	}

	/* Correct for discrete polygon graph: lavaCubicMeters function is an adaptation of the Shoelace formula
	for use in a discrete, grid-based context. It accounts for the differences between continuous geometry
	(where the Shoelace formula is precise) and discrete geometry (like pixels or tiles on a computer screen),
	where you have to consider the individual units that make up the shapes.
	Subtracting vertices.size / 2: This is a common adjustment in discrete geometry and is related to the concept of
	fenceposting (counting points on a grid or intersections in a lattice). Its a discrete analogue
	to the correction term in Pick's Theorem, which accounts for boundary points.
	Vertices are 'in the middle' of the area they represent, so we subtract half a unit from each vertex on the straight
	sides, corners cancel each other from 1/4 to 3/4 but there is an extra one in the end.
	###
	#.#
	###
	so here is area 9 even if it looks like 8 because vertices are in the middle of the area they represent
	*/
	fun lavaCubicMeters(vertices: List<Pair<Long, Long>>): Long {
		val interior = calculateArea(vertices) - vertices.size / 2L
		return interior + vertices.size
	}

	fun createPolygon(polygonInstructions: List<Task18.Instruction>): List<Pair<Long, Long>> {
		val polygon = mutableListOf<Pair<Long, Long>>()
		var currentX = 0L
		var currentY = 0L
		polygon.add(Pair(currentX, currentY))
		polygonInstructions.forEach { instruction ->
			val direction = Direction.fromString(instruction.direction)
			when (direction) {
				Direction.UP -> {
					for (i in 1..instruction.steps) {
						currentX--
						polygon.add(Pair(currentX, currentY))
					}
				}

				Direction.DOWN -> {
					for (i in 1..instruction.steps) {
						currentX++
						polygon.add(Pair(currentX, currentY))
					}
				}

				Direction.LEFT -> {
					for (i in 1..instruction.steps) {
						currentY--
						polygon.add(Pair(currentX, currentY))
					}
				}

				Direction.RIGHT -> {
					for (i in 1..instruction.steps) {
						currentY++
						polygon.add(Pair(currentX, currentY))
					}
				}
			}

		}
		return polygon
	}
	fun parseToPolygonInstructions(lines: List<String>, usingColor: Boolean): List<Instruction> {
		return if(usingColor) lines.map { line -> line.split(" ") }
			.map{line -> line[2].removeSurrounding("(", ")").drop(1)}
			.map { line -> Instruction(line.takeLast(1), line.take(5).toInt(16).toLong()) }
			.toList()
			else lines.map { line -> line.split(" ") }
			.map { line -> Instruction(line[0], line[1].toLong()) }
			.toList()
	}

	fun findBounds(polygon: List<Pair<Long, Long>>): Pair<Long, Long> {
		val maxX = polygon.maxOf { it.first }
		val maxY = polygon.maxOf { it.second }
		return maxX to maxY
	}

	fun printPolygon(polygon: List<Pair<Long, Long>>) {
		val bounds = findBounds(polygon)
		val maxX = bounds.first
		val maxY = bounds.second
		for (x in 0..maxX) {
			for (y in 0..maxY) {
				if (polygon.contains(Pair(x, y))) {
					print("#")
				} else {
					print(".")
				}
			}
			println()
		}
	}

}