package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import y2023.Task17.solvePart1

@DisplayName("Task17Test")
class Task17Test {

	private val input = """
        2413432311323
        3215453535623
        3255245654254
        3446585845452
        4546657867536
        1438598798454
        4457876987766
        3637877979653
        4654967986887
        4564679986453
        1224686865563
        2546548887735
        4322674655533
    """.trimIndent().lines()

	@Nested
	@DisplayName("Part 1")
	inner class Part1 {
		@Test
		fun `Matches example`() {
			val answer = Task17.solvePart1(input.map { s -> s.map { it.digitToInt() }.toIntArray() }.toTypedArray())

			assertEquals(102, answer)
		}

		@Test
		fun `Matches actual`() {
			val answer = Task17.a()

			assertEquals(1099, answer)
		}
	}

	@Nested
	@DisplayName("Part 2")
	inner class Part2 {
		@Test
		fun `Matches example`() {
			val answer = Task17.solvePart2(input.map { s -> s.map { it.digitToInt() }.toIntArray() }.toTypedArray())

			assertEquals(94, answer)
		}

		@Test
		fun `Matches actual`() {
			val answer = y2023.Task17.b()

			assertEquals(1266, answer)
		}
	}

}