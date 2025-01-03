package y2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Task8Test {

	val smallInput = """
		..........
		..........
		..........
		....a.....
		..........
		.....a....
		..........
		..........
		..........
		..........
	""".trimIndent()

	val testInput = """
		............
		........0...
		.....0......
		.......0....
		....0.......
		......A.....
		............
		............
		........A...
		.........A..
		............
		............
	""".trimIndent()

	@Test
	fun a_testInput() {
		assertEquals(14, Task8(testInput.split("\n").map { it.trim() }).a())
	}

	@Test
	fun a_smallInput() {
		assertEquals(2, Task8(smallInput.split("\n").map { it.trim() }).a())
	}

	@Test
	fun a() {
		val input = utils.readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_8.txt")
		val task = Task8(input)
		assertEquals(320, task.a())
	}

	@Test
	fun b_smallInput() {
		assertEquals(5, Task8(smallInput.split("\n").map { it.trim() }).b())
	}

	@Test
	fun b_testInput() {
		assertEquals(34, Task8(testInput.split("\n").map { it.trim() }).b())
	}

	@Test
	fun b() {
		val input = utils.readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_8.txt")
		val task = Task8(input)
		assertEquals(1157, task.b())
	}
}