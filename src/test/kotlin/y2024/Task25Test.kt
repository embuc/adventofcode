package y2024

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task25Test {

	val small_input = """
		#####
        .####
        .####
        .####
        .#.#.
        .#...
        .....
		""".trimIndent().lines()
	val test_input = """
		#####
        .####
        .####
        .####
        .#.#.
        .#...
        .....
        
        #####
        ##.##
        .#.##
        ...##
        ...#.
        ...#.
        .....
        
        .....
        #....
        #....
        #...#
        #.#.#
        #.###
        #####
        
        .....
        .....
        #.#..
        ###..
        ###.#
        ###.#
        #####
        
        .....
        .....
        .....
        #....
        #.#..
        #.#.#
        #####
    """.trimIndent().lines()
	@Test
	fun a() {
		assertEquals(0, Task25(small_input).a())
		assertEquals(3, Task25(test_input).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2024/2024_25.txt")
		assertEquals(3619, Task25(input).a())
	}

	@Test
	fun b() {
		//was not required
	}
}