package y2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsString

class Task11Test {

	@Test
	fun a() {
		assertEquals(3, Task11("se,sw,se,sw,sw").a())
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_11.txt")
		assertEquals(810, Task11(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_11.txt")
		assertEquals(1567, Task11(input).b())
	}
}