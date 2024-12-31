package y2024

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utils.readInputAsListOfStrings

class Task16Test {

	val testInput = """
		###############
		#.......#....E#
		#.#.###.#.###.#
		#.....#.#...#.#
		#.###.#####.#.#
		#.#.#.......#.#
		#.#.#####.###.#
		#...........#.#
		###.#.#####.#.#
		#...#.....#.#.#
		#.#.#.###.#.#.#
		#.....#...#.#.#
		#.###.#.#.#.#.#
		#S..#.....#...#
		###############
	""".trimIndent().lines()


	val testInput_2 = """
		#################
		#...#...#...#..E#
		#.#.#.#.#.#.#.#.#
		#.#.#.#...#...#.#
		#.#.#.#.###.#.#.#
		#...#.#.#.....#.#
		#.#.#.#.#.#####.#
		#.#...#.#.#.....#
		#.#.#####.#.###.#
		#.#.#.......#...#
		#.#.###.#####.###
		#.#.#...#.....#.#
		#.#.#.#####.###.#
		#.#.#.........#.#
		#.#.#.#########.#
		#S#.............#
		#################
	""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals(7036, Task16(testInput).a())
		assertEquals(11048, Task16(testInput_2).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_16.txt")
		assertEquals(85396, Task16(input).a())
	}

	@Test
	fun b() {
		assertEquals(45, Task16(testInput).b())
		assertEquals(64, Task16(testInput_2).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_16.txt")
		assertEquals(428, Task16(input).b())
	}
}