package y2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsListOfStrings

class Task17Test {

	val test_input1 = """
		Register A: 729
		Register B: 0
		Register C: 0
		
		Program: 0,1,5,4,3,0
	""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals("4,6,3,5,6,3,5,2,1,0", Task17(test_input1).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_17.txt")
		assertEquals(0, Task17(input).a())
	}

	@Test
	fun b() {
	}
}