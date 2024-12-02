package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import utils.readInputAsListOfStrings

class Task22Test {
	private val input = """
        1,0,1~1,2,1
        0,0,2~2,0,2
        0,2,3~2,2,3
        0,0,4~0,2,4
        2,0,5~2,2,5
        0,1,6~2,1,6
        1,1,8~1,1,9
    """.trimIndent().lines()

	@Nested
	@DisplayName("Part 1")
	inner class Part1 {
		@Test
		fun `Matches example`() {
			val answer = Task22(input).a()
			assertEquals(5, answer)
		}

		@Test
		fun `Matches actual`() {
			val answer =Task22(readInputAsListOfStrings("~/git/aoc-inputs/2023/2023_22.txt")).a()
			assertEquals(497, answer)
		}
	}

	@Nested
	@DisplayName("Part 2")
	inner class Part2 {
		@Test
		fun `Matches example`() {
			val answer = Task22(input).b()
			assertEquals(7, answer)
		}

		@Test
		fun `Matches actual`() {
			val answer = Task22(readInputAsListOfStrings("~/git/aoc-inputs/2023/2023_22.txt")).b()
			assertEquals(67468, answer)
		}
	}
}