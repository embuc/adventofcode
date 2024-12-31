package y2015

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task19Test {

	val testInput = """
        H => HO
        H => OH
        O => HH
		
		HOH
        """.trim().lines().map { it.trim() }
	val testInputB = """
        e => H
		e => O
		H => HO
		H => OH
		O => HH
		
		HOH
        """.trim().lines().map { it.trim() }

	@Test
	fun a() {
		assertEquals(4, Task19(testInput).a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_19.txt")
		assertEquals(509, Task19(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2015/2015_19.txt")
		assertEquals(195, Task19(input).b())
	}
}