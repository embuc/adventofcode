package y2023

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.readInput

class Uppg13Test {

	@Test
	fun testParseInputToRowsAndColumns() {
		val uppg13 = Uppg13()
		val input = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.
        """.trimIndent()

		val (rows, columns) = uppg13.parseInputToRowsAndColumns(input)

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
		val uppg13 = Uppg13()
		val input = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.
        """.trimIndent()

		val (rows, columns) = uppg13.parseInputToRowsAndColumns(input)
		val mirroredRows = uppg13.findMirroredCenters(rows, 100)
		val mirroredColumns = uppg13.findMirroredCenters(columns, 1)

		assertEquals(0, mirroredRows)
		assertEquals(5, mirroredColumns)
	}

	@Test
	fun testFindMirroredCentersRows() {
		val uppg13 = Uppg13()
		val input = """
			#...##..#
			#....#..#
			..##..###
			#####.##.
			#####.##.
			..##..###
			#....#..#
		""".trimIndent()

		val (rows, columns) = uppg13.parseInputToRowsAndColumns(input)
		val mirroredRows = uppg13.findMirroredCenters(rows, 100)
		val mirroredColumns = uppg13.findMirroredCenters(columns, 1)

		assertEquals(400, mirroredRows)
		assertEquals(0, mirroredColumns)
	}

	@Test
	fun partA() {
		val task = Uppg13()

		val input = readInput("Input13.txt")
		val sum = task.parseMultipleBlocks(input).sumOf { (rows, columns) ->
			task.findMirroredCenters(rows, 100) + task.findMirroredCenters(columns, 1)
		}
		assertEquals(35538, sum)
	}
}