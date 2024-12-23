package y2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task8Test {

	val small_input = """
		""
		"abc"
		"aaa\"aaa"
		"\x27"
	""".trimIndent().lines()
	val small_input2 = """
		"\\xmy"
	""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals(12, Task8(small_input).a())
		assertEquals(3, Task8(small_input2).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_8.txt")
		assertEquals(1333, Task8(input).a())
	}

	@Test
	fun b() {
		assertEquals(19, Task8(small_input).b())
		assertEquals(6, Task8(small_input2).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_8.txt")
		assertEquals(2046, Task8(input).b())
	}
}