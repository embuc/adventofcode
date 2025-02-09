package y2022

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task12Test {
	val testInput = """
		Sabqponm
		abcryxxl
		accszExk
		acctuvwj
		abdefghi
	""".trim().split("\n").map { it.trim() }.toList()

	@Test
	fun a() {
//		assertEquals(31, Task12(testInput).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2022/2022_12.txt")
		assertEquals(484, Task12(input).a())
	}

	@Test
	fun b() {
	}

}