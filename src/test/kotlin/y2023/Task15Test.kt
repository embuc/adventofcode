package y2023

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Task15Test {

	@Test
	fun testSolveAExample() {
		val input = """
			HASH
		""".trimIndent()

		val expected = 52L

		val actual = Task15.solveA(input.split(","))

		assertEquals(expected, actual)
	}

	@Test
	fun testA() {
		val expected = 0
		val actual = Task15.a()
		assertEquals(expected, actual)
	}

	@Test
	fun b() {
	}
}