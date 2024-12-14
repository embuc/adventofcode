package y2015

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsListOfStrings

class Task2Test {

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_2.txt")
		assertEquals(1588178, Task2(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_2.txt")
		assertEquals(3783758, Task2(input).b())
	}
}