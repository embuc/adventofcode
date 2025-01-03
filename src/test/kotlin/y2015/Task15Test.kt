package y2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task15Test {

	val testInput = """
		Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
		Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
		""".trimIndent().lines().map { it.trim() }

	@Test
	fun a() {
		assertEquals(62842880, Task15(testInput).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_15.txt")
		assertEquals(18965440, Task15(input).a())
	}

	@Test
	fun b() {
		assertEquals(57600000, Task15(testInput).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_15.txt")
		assertEquals(15862900, Task15(input).b())
	}
}