package y2023

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import y2023.Task14.EMPTY
import y2023.Task14.ROCK
import y2023.Task14.CUBE

class Task14Test {

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
		val actual = Task14.solveA(readInputAsListOfStrings("~/git/aoc-inputs/2023/2023_14.txt"))
		assertEquals(expected, actual)
	}

	@Test
	fun testSolveB() {
		val expected = 91286
		val actual = Task14.solveB(readInputAsListOfStrings("~/git/aoc-inputs/2023/2023_14.txt"))
		assertEquals(expected, actual)
	}

}