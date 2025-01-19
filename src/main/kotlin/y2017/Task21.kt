package y2017

import Task
import utils.countChar

//--- Day 21: Fractal Art ---
class Task21(val input:List<String>) :Task {

	override fun a(): Any {
		val rules = input.map { it.split(" => ") }.map { it[0] to it[1] }.toMap()
		val rotRules = mutableMapOf<String, String>()
		for(rule in rules) {
			var rotated = rule.key
			rotRules.put(rotated, rule.value)
			rotated = rotateGrid(rotated, 1)
			rotRules.put(rotated, rule.value)
			rotated = rotateGrid(rotated, 1)
			rotRules.put(rotated, rule.value)
			rotated = rotateGrid(rotated, 1)
			rotRules.put(rotated, rule.value)

			var flipped = flipGrid(rule.key,1)
			rotRules.put(flipped, rule.value)
			flipped = rotateGrid(flipped, 1)
			rotRules.put(flipped, rule.value)
			flipped = rotateGrid(flipped, 3)
			rotRules.put(flipped, rule.value)
			flipped = rotateGrid(flipped, 3)
			rotRules.put(flipped, rule.value)
		}

//		println("rules size: ${rules.size} rotatedRules size: ${rotRules.size}")
////		println(rotRules.keys)
		val grid = enhance(rotRules, 5)
		return countChar(grid, '#')
	}

	fun rotateGrid(grid: String, times: Int): String {
		var result = grid.split("/").map { it.toCharArray() }
		repeat(times) {
			result = result.mapIndexed { i, _ -> result.map { it[i] }.reversed().toCharArray() }
		}
		return result.joinToString("/") { it.joinToString("") }
	}

	fun flipGrid(grid: String, times: Int): String {
		var result = grid.split("/").map { it.toCharArray() }
		if (times == 1) {
			result = result.reversed()
		} else {
			result = result.map { it.reversed().toCharArray() }
		}
		return result.joinToString("/") { it.joinToString("") }
	}

	private fun enhance(rotatedRules: Map<String, String>, iterations: Int): List<String> {
		var grid = listOf(".#.", "..#", "###")
		for (i in 0 until iterations) {
			println("Iteration $i")
			val size = if (grid.size % 2 == 0) 2 else 3
			val newGrid = mutableListOf<String>()
			for (j in 0 until grid.size step size) {
				val row = mutableListOf<String>()
				for (k in 0 until grid.size step size) {
					val square = grid.subList(j, j + size).map { it.substring(k, k + size) }
					println(square.joinToString("/"))
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

	override fun b(): Any {
		return 0
	}
}