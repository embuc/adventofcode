package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Task16Test {

	@Test
	fun testA() {
		val expected = 7623
		val actual = Task16.a()
		assertEquals(expected, actual)
	}

	@Test
	fun testB() {
		val expected = 8244
		val actual = Task16.b()
		assertEquals(expected, actual)
	}

	@Test
	fun solveExampleA() {
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
//		println(input.joinToString("\n") { it.joinToString("") })
		val visited = Task16.solveGrid(input, Pair(0, 0), Task16.Direction.RIGHT)
		println(Task16.printDebugGrid(input, visited))
		val expected = 46
		val actual = visited.sumOf { booleanArray -> booleanArray.count() { it } }
		assertEquals(expected, actual)
	}
}
