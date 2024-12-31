package y2024

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task18Test {

	val testInput1 = """
		5,4
		4,2
		4,5
		3,0
		2,1
		6,3
		2,4
		1,5
		0,6
		3,3
		2,6
		5,1
		1,2
		5,5
		2,5
		6,5
		1,4
		0,4
		6,4
		1,1
		6,1
		1,0
		0,5
		1,6
		2,0
	""".trimIndent().lines()
	@Test
	fun a() {
		assertEquals(22, Task18(testInput1, 12).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_18.txt")
		assertEquals(304, Task18(input, 1024).a())
	}

	@Test
	fun b() {
		assertEquals("6,1", Task18(testInput1, 12).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_18.txt")
		assertEquals("50,28", Task18(input, 1024).b())
	}

}