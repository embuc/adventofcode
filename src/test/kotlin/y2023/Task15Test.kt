package y2023

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Task15Test {

	@Test
	fun testSolveAExample() {
		val input = """
			HASH
		""".trimIndent()

		val expected = 52

		val actual = input.split(",").sumOf { Task15.calcHash(it) }

		assertEquals(expected, actual)
	}

	@Test
	fun testA() {
		val expected = 498538
		val actual = Task15.a()
		assertEquals(expected, actual)
	}

	@Test
	fun b() {
		val expected = 286278
		val actual = Task15.b()
		assertEquals(expected, actual)
	}
}