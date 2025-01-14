package y2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsString

class Task8Test {

	@Test
	fun a() {
		val input= readInputAsString("~/git/aoc-inputs/2017/2017_8.txt")
		assertEquals(6343, Task8(input.split("\n")).a())
	}

	@Test
	fun b() {
		val input= readInputAsString("~/git/aoc-inputs/2017/2017_8.txt")
		assertEquals(7184, Task8(input.split("\n")).b())
	}
}