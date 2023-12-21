package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Task21Test {

	@Test
	fun a() {
		val input = """
			...........
			.....###.#.
			.###.##..#.
			..#.#...#..
			....#.#....
			.##..S####.
			.##..#...#.
			.......##..
			.##.#.####.
			.##..##.##.
			...........
		""".trimIndent().lines()
		val actual = Task21.solveA(input, steps = 6)
		assertEquals(16, actual)
	}

	@Test
	fun solveA() {
	}

	@Test
	fun b() {
	}

	@Test
	fun solveB() {
	}
}