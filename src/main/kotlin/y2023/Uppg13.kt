package y2023

import Task
import utils.readInput

class Uppg13: Task {

	override fun a(): Any {
		val input = readInput("Input13.txt")
//		var sum = 0
//		parseMultipleBlocks(input).forEach { (rows, columns) ->
//			sum += findMirroredCenters(rows, 100)
//			sum += findMirroredCenters(columns, 1)
//		}
//		return sum
//
		return parseMultipleBlocks(input).sumBy { (rows, columns) ->
			findMirroredCenters(rows, 100) + findMirroredCenters(columns, 1)
		}
	}


	override fun b(): Any {
		val input = readInput("Input13.txt");
		return -1
	}


	fun parseMultipleBlocks(input: String): List<Pair<List<String>, List<String>>> {
		// Splitting the input string into blocks
		val blocks = input.trim().split("\n\n")

		// Processing each block using the original method
		return blocks.map { block ->
			parseInputToRowsAndColumns(block)
		}
	}

	fun parseInputToRowsAndColumns(input: String): Pair<List<String>, List<String>> {
		// Splitting the input string into rows
		val rows = input.split('\n')

		// Initializing a list of StringBuilder for columns
		val columns = MutableList(rows[0].length) { StringBuilder() }

		// Extracting columns from the rows
		rows.forEach { row ->
			row.forEachIndexed { index, char ->
				columns[index].append(char)
			}
		}

		// Converting StringBuilder list to String list for columns
		val columnStrings = columns.map { it.toString() }

		return Pair(rows, columnStrings)
	}

	fun findMirroredCenters(lines: List<String>, factor: Int): Int {
		var center = 0

		for (i in 0 until lines.size - 1) {
			if (lines[i] == lines[i+1]) {
				println(lines[i])
				println(lines[i+1])

				center = i + 1
				if(checkIfMirror(lines, i)) {
					return center * factor
				}
				else {
					center = 0
				}
			}
		}

		return center
	}

	private fun checkIfMirror(lines: List<String>, i: Int): Boolean {
		var isMirror = true
		var offset = 1

		println("line sizes: ${lines.size}")
		while (i - offset >= 0 && i + offset < (lines.size-1)) {
			println("index down: " + (i - offset) + " value: " +lines[i - offset])
			println("index up: " + (i + 1 + offset) + " value: " +lines[i + 1 + offset])
			if (lines[i - offset] != lines[i + 1 + offset]) {
				isMirror = false
				break
			}
			offset++
		}

		return isMirror
	}
}