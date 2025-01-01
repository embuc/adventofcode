package y2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Task21Test {

	@Test
	fun a() {
		val actual = Task21.a()
		assertEquals(3574, actual)
	}

	@Test
	fun solveA() {
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
	fun b() {
		val actual = Task21.b()
		assertEquals(600090522932119, actual)
	}

}