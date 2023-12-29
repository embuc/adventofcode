package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import utils.readInputAsListOfStrings

class Task23Test {

	@Nested
	inner class Part1 {
		@Test
		fun `Matches example`() {
			val input = """
				#.#####################
				#.......#########...###
				#######.#########.#.###
				###.....#.>.>.###.#.###
				###v#####.#v#.###.#.###
				###.>...#.#.#.....#...#
				###v###.#.#.#########.#
				###...#.#.#.......#...#
				#####.#.#.#######.#.###
				#.....#.#.#.......#...#
				#.#####.#.#.#########v#
				#.#...#...#...###...>.#
				#.#.#v#######v###.###v#
				#...#.>.#...>.>.#.###.#
				#####v#.#.###v#.#.###.#
				#.....#...#...#.#.#...#
				#.#########.###.#.#.###
				#...###...#...#...#.###
				###.###.#.###v#####v###
				#...#...#.#.>.>.#.>.###
				#.###.###.#.###.#.#v###
				#.....###...###...#...#
				#####################.#
			""".trimIndent().lines()
			val answer = Task23(input).a()
			assertEquals(94, answer)
		}

		@Test
		fun `Matches actual`() {
			val answer = Task23(readInputAsListOfStrings("2023_23.txt")).a()
			assertEquals(2318, answer)
		}
	}

	@Nested
	inner class Part2 {
		@Test
		fun `Matches example`() {
			val input = """
				#.#####################
				#.......#########...###
				#######.#########.#.###
				###.....#.>.>.###.#.###
				###v#####.#v#.###.#.###
				###.>...#.#.#.....#...#
				###v###.#.#.#########.#
				###...#.#.#.......#...#
				#####.#.#.#######.#.###
				#.....#.#.#.......#...#
				#.#####.#.#.#########v#
				#.#...#...#...###...>.#
				#.#.#v#######v###.###v#
				#...#.>.#...>.>.#.###.#
				#####v#.#.###v#.#.###.#
				#.....#...#...#.#.#...#
				#.#########.###.#.#.###
				#...###...#...#...#.###
				###.###.#.###v#####v###
				#...#...#.#.>.>.#.>.###
				#.###.###.#.###.#.#v###
				#.....###...###...#...#
				#####################.#
			""".trimIndent().lines()
			val answer = Task23(input).b()
			assertEquals(154, answer)
		}

		@Test
		fun `Matches actual`() {
			val answer = Task23(readInputAsListOfStrings("2023_23.txt")).b()
			assertEquals(6426, answer)
		}
	}

}