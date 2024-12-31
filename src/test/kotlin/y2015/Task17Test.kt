package y2015

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task17Test {

	val test_input = """
		20
		15
		10
		5
		5
		""".trim().lines().map { it.trim() }
	@Test
	fun a() {
		assertEquals(4, Task17(test_input, 25).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_17.txt")
		assertEquals(4372, Task17(input, 150).a())
	}

	@Test
	fun b() {
	}
}