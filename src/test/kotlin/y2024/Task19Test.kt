package y2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsListOfStrings

class Task19Test {

//	white (w), blue (u), black (b), red (r), or green (g)
	val small_input = """
		r, wr, b

		brwrr
	""".trimIndent().lines()
	val small_input2 = """
		r, wr, b

		brg
	""".trimIndent().lines()
	val test_input = """
		r, wr, b, g, bwu, rb, gb, br

		brwrr
		bggr
		gbbr
		rrbgbr
		ubwu
		bwurrg
		brgr
		bbrgwb
	""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals(1, Task19(small_input).a())
		assertEquals(0, Task19(small_input2).a())
		assertEquals(6, Task19(test_input).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_19.txt")
		assertEquals(304, Task19(input).a())
	}

	@Test
	fun b() {
		assertEquals(1L, Task19(small_input).b())
		assertEquals(0L, Task19(small_input2).b())
		assertEquals(16L, Task19(test_input).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_19.txt")
		assertEquals(304L, Task19(input).b())
	}
}