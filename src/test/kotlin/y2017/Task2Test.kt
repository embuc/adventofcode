package y2017

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task2Test {

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2017/2017_2.txt")
		assertEquals(46402, Task2(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2017/2017_2.txt")
		assertEquals(265, Task2(input).b())
	}
}