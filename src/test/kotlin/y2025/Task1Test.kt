package y2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task1Test {

	val input: List<String> = """
	L68
	L30
	R48
	L5
	R60
	L55
	L1
	L99
	R14
	L82
""".trimIndent().trim().lines()

	@Test
	fun a_smallInput() {
		assertEquals(3, Task1(input).a())
	}

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2025/2025_1.txt")
		assertEquals(3, Task1(input).a())
	}

	@Test
	fun b() {
	}

}