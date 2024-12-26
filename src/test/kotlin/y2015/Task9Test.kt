package y2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task9Test {

	val test_input = """
		London to Dublin = 464
        London to Belfast = 518
        Dublin to Belfast = 141
    """.trimIndent().lines()

	@Test
	fun a() {
        assertEquals(605, Task9(test_input).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_9.txt")
		assertEquals(207, Task9(input).a())
	}

	@Test
	fun b() {
		assertEquals(982, Task9(test_input).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_9.txt")
		assertEquals(804, Task9(input).b())
	}
}