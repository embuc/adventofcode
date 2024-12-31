package y2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings

class Task18Test {

	val test_input = """
		.#.#.#
        ...##.
        #....#
        ..#...
        #.#..#
        ####..
        """.trim().lines().map { it.trim() }
	val test_input_b = """
		##.#.#
		...##.
		#....#
		..#...
		#.#..#
		####.#
        """.trim().lines().map { it.trim() }

	@Test
	fun a() {
		assertEquals(4, Task18(test_input).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_18.txt")
		assertEquals(1061, Task18(input, 100).a())
	}

	@Test
	fun b() {
		assertEquals(17, Task18(test_input_b, 5).b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_18.txt")
		assertEquals(1006, Task18(input, 100).b())
	}
}