package y2023

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.readInputAsString

class Task13Test {

	private val task = Task13

	@Test
	fun testParseInputToRowsAndColumns() {
		val input = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.
        """.trimIndent()

		val (rows, columns) = task.parseInputToRowsAndColumns(input)

		val expectedRows = listOf(
			"#.##..##.",
			"..#.##.#.",
			"##......#",
			"##......#",
			"..#.##.#.",
			"..##..##.",
			"#.#.##.#."
		)

		val expectedColumns = listOf(
			"#.##..#",
			"..##...",
			"##..###",
			"#....#.",
			".#..#.#",
			".#..#.#",
			"#....#.",
			"##..###",
			"..##..."
		)

		assertEquals(expectedRows, rows)
		assertEquals(expectedColumns, columns)
	}

	@Test
	fun testFindMirroredCentersColumns() {
		val input = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.
        """.trimIndent()

		val (rows, columns) = task.parseInputToRowsAndColumns(input)
		val mirroredRows = task.findMirroredCenters(rows, 100)
		val mirroredColumns = task.findMirroredCenters(columns, 1)

		assertEquals(0, mirroredRows)
		assertEquals(5, mirroredColumns)
	}

	@Test
	fun testFindMirroredCentersRows() {
		val input = """
			#...##..#
			#....#..#
			..##..###
			#####.##.
			#####.##.
			..##..###
			#....#..#
		""".trimIndent()

		val (rows, columns) = task.parseInputToRowsAndColumns(input)
		val mirroredRows = task.findMirroredCenters(rows, 100)
		val mirroredColumns = task.findMirroredCenters(columns, 1)

		assertEquals(400, mirroredRows)
		assertEquals(0, mirroredColumns)
	}

	@Test
	fun partA() {

		val input = readInputAsString("2023_13.txt")
		val sum = task.parseMultipleBlocks(input).sumOf { (rows, columns) ->
			task.findMirroredCenters(rows, 100) + task.findMirroredCenters(columns, 1)
		}
		assertEquals(35538, sum)
	}

	@Test
	fun partB() {
	    val result = task.b()
		assertEquals(30442, result)
	}
}