package y2017

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task10Test {

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_10.txt")
		assertEquals(4480, Task10(input, 255).a())
	}

	@Test
	fun b() {
        assertEquals("33efeb34ea91902bb2f59c9920caa6cd", Task10("AoC 2017", 255).b())
        val input = readInputAsString("~/git/aoc-inputs/2017/2017_10.txt")
        assertEquals("c500ffe015c83b60fad2e4b7d59dabc4", Task10(input, 255).b())
	}
}