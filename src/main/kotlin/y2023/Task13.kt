package y2023

import Task
import utils.readInputAsString

// --- Day 13: Point of Incidence ---
// Interesting problem, but I don't think I solved it in the most elegant way.
// It reads ok, but I've seen some other solutions which are much shorter (and possibly more elegant).
object Task13 : Task {

	override fun a(): Any {
		val input = readInputAsString("2023_13.txt")
		return parseMultipleBlocks(input).sumOf { (rows, columns) ->
			findMirroredCenters(rows, 100) + findMirroredCenters(columns, 1)
		}
	}

	override fun b(): Any {
		val input = readInputAsString("~/git/aoc-inputs/2023/2023_13.txt")

		return parseMultipleBlocks(input)
			.sumOf { (rows, columns) ->
				100 * getNrBeforeSmudgeReflection(rows, false) + getNrBeforeSmudgeReflection(rows, true)
			}
	}

	private fun getNrBeforeSmudgeReflection(pattern: List<String>, columns: Boolean): Int {
		val max = if (columns) pattern[0].length else pattern.size
		for (i in 1 until max) {
			val prev = if (columns) getColumn(i - 1, pattern) else pattern[i - 1]
			val current = if (columns) getColumn(i, pattern) else pattern[i]

			// First, find two rows/columns which are the same or are different by one smudge.
			val initialSmudges = getNrSmudges(prev, current)
			if (initialSmudges == 0 || initialSmudges == 1) {
				// Match found, now find out how many rows/columns we need to check.
				val elementsToCheck = minOf(i - 1, max - i - 1)
				var matches = true
				var smudgeFound = false
				for (el in 0 until elementsToCheck) {
					val first = if (columns) getColumn(i - el - 2, pattern) else pattern[i - el - 2]
					val second = if (columns) getColumn(i + el + 1, pattern) else pattern[i + el + 1]
					val nrSmudges = getNrSmudges(first, second)

					// Check for smudge conditions
					if (nrSmudges > 1 || (initialSmudges == 1 && nrSmudges == 1) || (nrSmudges == 1 && smudgeFound)) {
						matches = false
						break
					} else if (nrSmudges == 1) {
						smudgeFound = true
					}
				}

				// Check final condition for returning the index
				if (matches && ((initialSmudges == 1) xor smudgeFound)) {
					return i
				}
			}
		}
		return 0
	}

	private fun getColumn(x: Int, pattern: List<String>): String {
		val sb = StringBuilder()
		for (y in pattern.indices) {
			val line = pattern[y]
			sb.append(line[x])
		}
		return sb.toString()
	}

	private fun getNrSmudges(s1: String, s2: String): Int {
		var total = 0
		for (i in s1.indices) {
			if (s1[i] != s2[i]) {
				total++
			}
		}
		return total
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

	fun findMirroredCenters(lines: List<String>, factor: Int, original: Int = -1): Int {
		for (i in 0 until lines.size - 1) {
			if (lines[i] == lines[i + 1] && (original != (i + 1))) {
				if (checkIfMirror(lines, i)) {
					return (i + 1) * factor
				}
			}
		}

		return 0
	}

	private fun checkIfMirror(lines: List<String>, i: Int): Boolean {
		var isMirror = true
		var offset = 1

		while (((i - offset) >= 0) && ((i + 1 + offset) < lines.size)) {
			if (lines[i - offset] != lines[i + 1 + offset]) {
				isMirror = false
				break
			}
			offset++
		}

		return isMirror
	}

}