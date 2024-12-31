package y2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Task14Test {

	val testInput = """
		p=0,4 v=3,-3
		p=6,3 v=-1,-3
		p=10,3 v=-1,2
		p=2,0 v=2,-1
		p=0,0 v=1,3
		p=3,0 v=-2,-2
		p=7,6 v=-1,-3
		p=3,0 v=-1,-2
		p=9,3 v=2,3
		p=7,3 v=-1,2
		p=2,4 v=2,-3
		p=9,5 v=-3,-3
	""".trimIndent().lines()

	@Test
	fun a() {
		// test input 11 wide 7 tall
		assertEquals(12L, Task14(testInput).a(11,7))
		//real input 101 wide 103 tall
		val input = utils.readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_14.txt")
		assertEquals(215987200L, Task14(input).a())
	}

	@Test
	fun b() {
		val input = utils.readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_14.txt")
		assertEquals(8050, Task14(input).b())
	}
}