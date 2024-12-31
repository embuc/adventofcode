package y2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task8Test {

	val smallInput = """
		""
		"abc"
		"aaa\"aaa"
		"\x27"
	""".trimIndent().lines()
	val smallInput2 = """
		"\\xmy"
	""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals(12, Task8(smallInput).a())
		assertEquals(3, Task8(smallInput2).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_8.txt")
		assertEquals(1333, Task8(input).a())
	}

	@Test
	fun b() {
		assertEquals(19, Task8(smallInput).b())
		assertEquals(6, Task8(smallInput2).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_8.txt")
		assertEquals(2046, Task8(input).b())
	}
}