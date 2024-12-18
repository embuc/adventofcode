package y2015

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsListOfStrings

class Task6Test {

	val test_input1 = """
		turn on 0,0 through 999,999
	""".trimIndent().lines()
	val test_input2 = """
		turn on 0,0 through 499,0
		toggle 0,0 through 999,0
	""".trimIndent().lines()
	val test_input3 = """
		turn on 0,0 through 999,999
		turn off 499,499 through 500,500
	""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals(1_000_000, Task6(test_input1).a())
		assertEquals(500, Task6(test_input2).a())
		assertEquals(999996, Task6(test_input3).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_6.txt")
		assertEquals(569999, Task6(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_6.txt")
		assertEquals(17836115, Task6(input).b())
	}
}