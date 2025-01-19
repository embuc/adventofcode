package y2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task18Test {

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2017/2017_18.txt")
		assertEquals(3423, Task18(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2017/2017_18.txt")
//		135 too low
		assertEquals(7493, Task18(input).b())
	}
}