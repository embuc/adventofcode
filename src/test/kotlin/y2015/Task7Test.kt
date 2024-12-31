package y2015

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsListOfStrings

class Task7Test {

	val testInput = """
		123 -> x
		456 -> y
		x AND y -> d
		x OR y -> e
		x LSHIFT 2 -> f
		y RSHIFT 2 -> g
		NOT x -> h
		NOT y -> i
	""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals(65079, Task7(testInput).processInstructions(testInput, "i"))
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_7.txt")
		assertEquals(956, Task7(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_7.txt")
		assertEquals(40149, Task7(input).b())
	}
}