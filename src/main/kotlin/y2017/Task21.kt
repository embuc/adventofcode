package y2017

import Task
import utils.countChar

//--- Day 21: Fractal Art ---
class Task21(val input:List<String>) :Task {

	override fun a(): Any {
		val rules = input.map { it.split(" => ") }.associate { it[0] to it[1] }
		val rotRules = prepareRotatedRules(rules)
		val grid = enhance(rotRules, 5)
		return countChar(grid, '#')
	}

	override fun b(): Any {
		val rules = input.map { it.split(" => ") }.associate { it[0] to it[1] }
		val rotRules = prepareRotatedRules(rules)
		val grid = enhance(rotRules, 18)
		return countChar(grid, '#')
	}

	private fun prepareRotatedRules(rules: Map<String, String>): MutableMap<String, String> {
		val rotRules = mutableMapOf<String, String>()
		for (rule in rules) {
			var rotated = rule.key
			rotRules[rotated] = rule.value
			rotated = rotateGrid(rotated, 1)
			rotRules[rotated] = rule.value
			rotated = rotateGrid(rotated, 1)
			rotRules[rotated] = rule.value
			rotated = rotateGrid(rotated, 1)
			rotRules[rotated] = rule.value

			var flipped = flipGrid(rule.key)
			rotRules[flipped] = rule.value
			flipped = rotateGrid(flipped, 1)
			rotRules[flipped] = rule.value
			flipped = rotateGrid(flipped, 3)
			rotRules[flipped] = rule.value
			flipped = rotateGrid(flipped, 3)
			rotRules[flipped] = rule.value
		}
		return rotRules
	}

	private fun rotateGrid(grid: String, times: Int): String {
		var result = grid.split("/").map { it.toCharArray() }
		repeat(times) {
			result = List(result.size) { i -> result.map { it[i] }.reversed().toCharArray() }
		}
		return result.joinToString("/") { it.joinToString("") }
	}

	private fun flipGrid(grid: String): String {
		var result = grid.split("/").map { it.toCharArray() }
		result = result.reversed()
		return result.joinToString("/") { it.joinToString("") }
	}

	private fun enhance(rotatedRules: Map<String, String>, iterations: Int): List<String> {
		var grid = listOf(".#.", "..#", "###")
		for (i in 0 until iterations) {
			val size = if (grid.size % 2 == 0) 2 else 3
			val newGrid = mutableListOf<String>()
			for (j in grid.indices step size) {
				val row = mutableListOf<String>()
				for (k in grid.indices step size) {
					val square = grid.subList(j, j + size).map { it.substring(k, k + size) }
					val enhanced = rotatedRules[square.joinToString("/")]
					row.add(enhanced!!)
				}
				val newRows = row[0].split("/").indices.map { "" }.toMutableList()
				for (r in row) {
					val split = r.split("/")
					for (l in split.indices) {
						newRows[l] += split[l]
					}
				}
				newGrid.addAll(newRows)
			}
			grid = newGrid
		}
		return grid
	}

}