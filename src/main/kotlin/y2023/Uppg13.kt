package y2023

import Task
import utils.readInput

class Uppg13: Task {

	override fun a(): Any {
		val input = readInput("Input13.txt")
		return parseMultipleBlocks(input).sumBy { (rows, columns) ->
			findMirroredCenters(rows, 100) + findMirroredCenters(columns, 1)
		}
	}

	override fun b(): Any {
		val input = readInput("Input13.txt");
		return -1
	}

	fun parseMultipleBlocks(input: String): List<Pair<List<String>, List<String>>> {
		val blocks = input.trim().split("\n\n")
		return blocks.map { block ->
			parseInputToRowsAndColumns(block)
		}
	}

	fun parseInputToRowsAndColumns(input: String): Pair<List<String>, List<String>> {
		val rows = input.split('\n')
		val columns = MutableList(rows[0].length) { StringBuilder() }
		rows.forEach { row ->
			row.forEachIndexed { index, char ->
				columns[index].append(char)
			}
		}
		val columnStrings = columns.map { it.toString() }
		return Pair(rows, columnStrings)
	}

	fun findMirroredCenters(lines: List<String>, factor: Int): Int {
		for (i in 0 until lines.size - 1) {
			if (lines[i] == lines[i+1]) {
				if(checkIfMirror(lines, i)) {
					return (i+1) * factor
				}
			}
		}

		return 0
	}

	private fun checkIfMirror(lines: List<String>, i: Int): Boolean {
		var isMirror = true
		var offset = 1

		while (i - offset >= 0 && i + offset < (lines.size-1)) {
			if (lines[i - offset] != lines[i + 1 + offset]) {
				isMirror = false
				break
			}
			offset++
		}

		return isMirror
	}
}