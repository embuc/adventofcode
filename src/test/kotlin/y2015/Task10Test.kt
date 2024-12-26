package y2015

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task10Test {

	@Test
	fun a() {
	    assertEquals(6, Task10(Pair("1", 4)).a())
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_10.txt")
		assertEquals(329356, Task10(Pair(input.trim(), 40)).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_10.txt")
		assertEquals(4666278, Task10(Pair(input.trim(), 50)).a())
	}
}