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

	val inputB: List<String> = """
	L150
""".trimIndent().trim().lines()

	@Test
	fun a_smallInput() {
		assertEquals(3, Task1(input).a())
	}

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2025/2025_1.txt")
		assertEquals(1066, Task1(input).a())
	}

	@Test
	fun b_smallInput() {
		assertEquals(6, Task1(input).b())
	}

	@Test
	fun b_smallInput2() {
		assertEquals(2, Task1(inputB).b())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2025/2025_1.txt")
		assertEquals(6223, Task1(input).b())
	}

}