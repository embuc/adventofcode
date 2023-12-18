package y2023

import Task
import utils.getLinesFromFile
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
					else -> throw IllegalArgumentException("Unknown direction: $value")
				}
			}
		}
	}

	data class Instruction(val direction: Direction, val steps: Int, val color: String)

	override fun a(): Any {
		val lines = getLinesFromFile("2023_18.txt")
		val polygonInstructions = parseToPolygonInstructions(lines)
		val createPolygon = createPolygon(polygonInstructions)
		return lavaCubicMeters(createPolygon)
	}

	override fun b(): Any {
		val lines = getLinesFromFile("2023_18.txt")
		val polygonInstructions = parseToPolygonInstructions(lines)
		val createPolygon = createPolygon(polygonInstructions)
		return -1

	}

	/*	Shoelace formula
	*  Area = |(1/2) * Σ(from i=1 to n-1) of (x_i * y_(i+1) - x_(i+1) * y_i) + (x_n * y_1 - x_1 * y_n)|
	*  */
	fun calculateArea(vertices: List<Pair<Int, Int>>): Int {
		var area = 0
		val n = vertices.size

		for (i in 0 until n ) {
			val j = (i + 1) % n
			area += vertices[i].first * vertices[j].second
			area -= vertices[j].first * vertices[i].second
		}

		return Math.abs(area / 2)
	}

	fun lavaCubicMeters(vertices: List<Pair<Int, Int>>): Int {
		val interior = calculateArea(vertices) - vertices.size / 2
		return interior + vertices.size
	}

	fun createPolygon(polygonInstructions: List<Task18.Instruction>): List<Pair<Int, Int>> {
		val polygon = mutableListOf<Pair<Int, Int>>()
		var currentX = 0
		var currentY = 0
		polygon.add(Pair(currentX, currentY))
		polygonInstructions.forEach { instruction ->
			when (instruction.direction) {
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
	fun parseToPolygonInstructions(lines: List<String>): List<Instruction> {
		return lines.map { line -> line.split(" ") }
			.map { line -> Instruction(Direction.fromString(line[0]), line[1].toInt(), line[2]) }
			.toList()
	}

	fun findBounds(polygon: List<Pair<Int, Int>>): Pair<Int, Int> {
		val maxX = polygon.maxOf { it.first }
		val maxY = polygon.maxOf { it.second }
		return maxX to maxY
	}

	fun printPolygon(polygon: List<Pair<Int, Int>>) {
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