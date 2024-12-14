package y2015

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsString

class Task1Test {

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_1.txt")
		assertEquals(280, Task1(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_1.txt")
		assertEquals(1797, Task1(input).b())
	}
}