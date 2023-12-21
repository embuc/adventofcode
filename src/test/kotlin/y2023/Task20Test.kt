package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

class Task20Test {

	@Test
	fun a() {
		val actual = Task20.a()
		assertEquals(818649769, actual)
	}

	@Test
	fun b() {
		val actual = Task20.b()
		assertEquals(0, actual)
	}

	private val input = """
        broadcaster -> a
        %a -> inv, con
        &inv -> b
        %b -> con
        &con -> output
    """.trimIndent().lines()

	@Nested
	@DisplayName("Part 1")
	inner class Part1 {
		@Test
		fun `Matches example`() {
			val answer = y2023.Task20.solveA(input)

			assertEquals(11_687_500, answer)
		}

		@Test
		fun `Matches actual`() {
			val answer = y2023.Task20.a()
			assertEquals(818_649_769, answer)
		}
	}

	@Nested
	@DisplayName("Part 2")
	inner class Part2 {
		@Test
		fun `Matches actual`() {
			val answer = y2023.Task20.b()
			assertEquals(246_313_604_784_977, answer)
		}
	}
}