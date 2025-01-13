package y2017

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task4Test {

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2017/2017_4.txt")
		assertEquals(325, Task4(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2017/2017_4.txt")
		assertEquals(119, Task4(input).b())
	}
}