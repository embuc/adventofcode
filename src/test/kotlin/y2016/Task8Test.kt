package y2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task8Test {

	val test_input = """
		rect 3x2
		rotate column x=1 by 1
		rotate row y=0 by 4
		rotate column x=1 by 1
		""".trim().lines().map { it.trim() }

	@Test
	fun a() {
		val task = Task8(test_input, 7, 3)
        assertEquals(6, task.a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_8.txt")
		assertEquals(128, Task8(input, 50, 6).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_8.txt")
		assertEquals("EOARGPHYAO", Task8(input, 50, 6).b())
	}
}