package y2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task20Test {

	val testInput = """
		###############
		#...#...#.....#
		#.#.#.#.#.###.#
		#S#...#.#.#...#
		#######.#.#.###
		#######.#.#...#
		#######.#.###.#
		###..E#...#...#
		###.#######.###
		#...###...#...#
		#.#####.#.###.#
		#.#...#.#.#...#
		#.#.#.#.#.#.###
		#...#...#...###
		###############
	""".trimIndent().lines()

	//	There are 14 cheats that save 2 picoseconds.
//	There are 14 cheats that save 4 picoseconds.
//	There are 2 cheats that save 6 picoseconds.
//	There are 4 cheats that save 8 picoseconds.
//	There are 2 cheats that save 10 picoseconds.
//	There are 3 cheats that save 12 picoseconds.
//	There is one cheat that saves 20 picoseconds.
//	There is one cheat that saves 36 picoseconds.
//	There is one cheat that saves 38 picoseconds.
//	There is one cheat that saves 40 picoseconds.
//	There is one cheat that saves 64 picoseconds.
	@Test
	fun a() {
		assertEquals(5, Task20(testInput, 20).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_20.txt")
		assertEquals(1452, Task20(input, 100).a())
	}

//	There are 32 cheats that save 50 picoseconds.
//	There are 31 cheats that save 52 picoseconds.
//	There are 29 cheats that save 54 picoseconds.
//	There are 39 cheats that save 56 picoseconds.
//	There are 25 cheats that save 58 picoseconds.
//	There are 23 cheats that save 60 picoseconds.
//	There are 20 cheats that save 62 picoseconds.
//	There are 19 cheats that save 64 picoseconds.
//	There are 12 cheats that save 66 picoseconds.
//	There are 14 cheats that save 68 picoseconds.
//	There are 12 cheats that save 70 picoseconds.
//	There are 22 cheats that save 72 picoseconds.
//	There are 4 cheats that save 74 picoseconds.
//	There are 3 cheats that save 76 picoseconds.
	@Test
	fun b() {
		assertEquals(7, Task20(testInput, 74).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_20.txt")
		assertEquals(999556, Task20(input, 100).b())
	}
}