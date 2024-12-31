package y2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task19Test {

//	white (w), blue (u), black (b), red (r), or green (g)
	val smallInput = """
		r, wr, b

		brwrr
	""".trimIndent().lines()
	val smallInput2 = """
		r, wr, b

		brg
	""".trimIndent().lines()
	val testInput = """
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
		assertEquals(1, Task19(smallInput).a())
		assertEquals(0, Task19(smallInput2).a())
		assertEquals(6, Task19(testInput).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_19.txt")
		assertEquals(304, Task19(input).a())
	}

	@Test
	fun b() {
		assertEquals(1L, Task19(smallInput).b())
		assertEquals(0L, Task19(smallInput2).b())
		assertEquals(16L, Task19(testInput).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_19.txt")
		assertEquals(705756472327497L, Task19(input).b())
	}
}