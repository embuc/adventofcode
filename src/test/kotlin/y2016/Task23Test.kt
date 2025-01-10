package y2016

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task23Test {

	val testInput = """
		cpy 2 a
        tgl a
        tgl a
        tgl a
        cpy 1 a
        dec a
        dec a
        """.trim().lines().map { it.trim() }

	@Test
	fun a() {
//		assertEquals(3, Task23(testInput).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_23.txt")
		assertEquals(12516, Task23(input).a())
	}

	@Test
	fun b() {
		//changed loop into multiplication + some noop instructions
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_23_B.txt")
		assertEquals(479009076, Task23(input).b())
	}
}