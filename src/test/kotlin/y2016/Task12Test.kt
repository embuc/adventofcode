package y2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsString

class Task12Test {

	@Test
	fun a() {
		val testInput = """
            cpy 41 a
            inc a
            inc a
            dec a
            jnz a 2
            dec a
        """.trimIndent().lines()
		assertEquals(42, Task12(testInput).a())
		val input = readInputAsString("~/git/aoc-inputs/2016/2016_12.txt")
		assertEquals(318083, Task12(input.lines()).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2016/2016_12.txt")
		assertEquals(9227737, Task12(input.lines()).b())
	}
}