package y2015

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsString

class Task4Test {

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_4.txt")
		assertEquals(282749, Task4(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_4.txt")
		assertEquals(9962624, Task4(input).b())
	}
}