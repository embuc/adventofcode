package y2023

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import y2023.Task14.EMPTY
import y2023.Task14.ROCK
import y2023.Task14.CUBE

class Task14Test {


	@Test
	fun testMapStringToGrid() {
		val stringLines = """
            O....#....
            O.OO#....#
            .....##...
            OO.#O....O
            .O.....O#.
            O.#..O.#.#
            ..O..#O..O
            .......O..
            #....###..
            #OO..#....
        """.trimIndent().split("\n")

		val expectedGrid = arrayOf(
			intArrayOf(1, 0, 0, 0, 0, 2, 0, 0, 0, 0),
			intArrayOf(1, 0, 1, 1, 2, 0, 0, 0, 0, 2),
			intArrayOf(0, 0, 0, 0, 0, 2, 2, 0, 0, 0),
			intArrayOf(1, 1, 0, 2, 1, 0, 0, 0, 0, 1),
			intArrayOf(0, 1, 0, 0, 0, 0, 0, 1, 2, 0),
			intArrayOf(1, 0, 2, 0, 0, 1, 0, 2, 0, 2),
			intArrayOf(0, 0, 1, 0, 0, 2, 1, 0, 0, 1),
			intArrayOf(0, 0, 0, 0, 0, 0, 0, 1, 0, 0),
			intArrayOf(2, 0, 0, 0, 0, 2, 2, 2, 0, 0),
			intArrayOf(2, 1, 1, 0, 0, 2, 0, 0, 0, 0)
		)

		val actualGrid = Task14.parseToGrid(stringLines)

		// Assert that the actual grid matches the expected grid
		assert(actualGrid.size == expectedGrid.size) { "Row count mismatch" }
		actualGrid.forEachIndexed { index, row ->
			assertArrayEquals(expectedGrid[index], row, "Mismatch at row $index")
		}
	}

	@Test
	fun testParseToGrid() {
		val input = """
			.#.
			O#.
			...
		""".trimIndent()

		val expected = arrayOf(
			intArrayOf(EMPTY, CUBE, EMPTY),
			intArrayOf(ROCK, CUBE, EMPTY),
			intArrayOf(EMPTY, EMPTY, EMPTY)
		)

		val actual = Task14.parseToGrid(input.lines()).toTypedArray()

		assertArrayEquals(expected, actual)
	}

	@Test
	fun testSolveAExample() {
		val input = """
			.#.
			O#.
			...
		""".trimIndent()

		val expected = 3

		val actual = Task14.solveA(input.lines())

		assertEquals(expected, actual)
	}

	@Test
	fun testSolveBExample() {
		val input = """
			.#.
			O#.
			...
		""".trimIndent()

		val expected = 1

		val actual = Task14.solveB(input.lines())

		assertEquals(expected, actual)
	}

	@Test
	fun testSolveA() {
		val expected = 105784
		val actual = Task14.solveA(readInputAsListOfStrings("2023_14.txt"))
		assertEquals(expected, actual)
	}

	@Test
	fun testSolveB() {
		val expected = 91286
		val actual = Task14.solveB(readInputAsListOfStrings("2023_14.txt"))
		assertEquals(expected, actual)
	}

}