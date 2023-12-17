package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Task16Test {

	@Test
	fun a() {
		val expected = 7623
		val actual = Task16.a()
		assertEquals(expected, actual)
	}

	@Test
	fun b() {
	}

	@Test
	fun solve() {
		val input = """
			.|...\....
			|.-.\.....
			.....|-...
			........|.
			..........
			.........\
			..../.\\..
			.-.-/..|..
			.|....-|.\
			..//.|....
		""".trimIndent().split("\n").map { it.toCharArray() }.toTypedArray()
		println(input.joinToString("\n") { it.joinToString("") })
		println()
		val visited = Task16.solveA(input)
		println(Task16.printDebugGrid(input, visited))
		val expected = 46
		val actual = visited.sumOf { booleanArray -> booleanArray.count() { it } }
		assertEquals(expected, actual)
	}
}
