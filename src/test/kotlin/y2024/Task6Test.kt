package y2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsListOfStrings

class Task6Test {

	val small_input = """
		....#.....
		.........#
		..........
		..#.......
		.......#..
		..........
		.#..^.....
		........#.
		#.........
		......#...
	""".trimIndent();

	val small_input2 = """
		....#.....
		...#.#....
		.#..^.....
	""".trimIndent();

	val small_input3 = """
		.....
		..#..
		..^.#
		...#.
		.....
	""".trimIndent();

	val small_input4 = """
		....#
		...#.
		..^..
		.....
		..#..
	""".trimIndent();

	val small_input5 = """
		.....
		..#..
		#.^..
		.....
		.....
	""".trimIndent();
//	This example should be 11 for part 1 (and 6 for part 2
	val small_input6 = """
		########
		#......#
		...^...#
		########
	""".trimIndent();

	@Test
	fun a_small_input() {
		val input = small_input.split("\n")
		val task = Task6(input)
		assertEquals(41, task.a())
	}

	@Test
	fun a_small_input2() {
		val input = small_input2.split("\n")
		val task = Task6(input)
		assertEquals(2, task.a())
	}

	@Test
	fun a_small_input3() {
		val input = small_input3.split("\n")
		val task = Task6(input)
		assertEquals(4, task.a())
	}
	@Test
	fun a_small_input4() {
		val input = small_input4.split("\n")
		val task = Task6(input)
		assertEquals(3, task.a())
	}
	@Test
	fun a_small_input5() {
		val input = small_input5.split("\n")
		val task = Task6(input)
		assertEquals(3, task.a())
	}
	@Test
	fun a_small_input6() {
		val input = small_input6.split("\n")
		val task = Task6(input)
		assertEquals(11, task.a())
	}

	@Test
	fun a() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_6.txt")
		val task = Task6(input)
		assertEquals(5305, task.a())
	}

	@Test
	fun b() {
	}
}