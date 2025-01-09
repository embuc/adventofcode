package y2016

import org.junit.jupiter.api.Test
import utils.readInputAsListOfStrings
import kotlin.test.assertEquals

class Task21Test {

	val testInput = """
		swap position 4 with position 0
		swap letter d with letter b
		reverse positions 0 through 4
		rotate left 1 step
		move position 1 to position 4
		move position 3 to position 0
		rotate based on position of letter b
		rotate based on position of letter d
	""".trim().lines().map { it.trim() }

	@Test
	fun a() {
		assertEquals("decab", Task21(testInput, "abcde").a())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_21.txt")
		assertEquals("ghfacdbe", Task21(input, "abcdefgh").a())
	}

	@Test
	fun b() {
		assertEquals("abcde", Task21(testInput.reversed(), "decab").b())
		val input = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_21.txt").reversed()
		assertEquals("abcdefgh", Task21(input, "ghfacdbe").b())
		assertEquals("fhgcdaeb", Task21(input, "fbgdceah").b())
	}
}