package y2015

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsString

class Task3Test {

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_3.txt")
		assertEquals(2081, Task3(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_3.txt")
		assertEquals(2341, Task3(input).b())
	}
}