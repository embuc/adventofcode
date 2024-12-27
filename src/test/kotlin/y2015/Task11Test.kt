package y2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsString

class Task11Test {

	@Test
	fun a() {
		assertEquals("ghjaabcc", Task11("ghijklmn").a())
		val input = readInputAsString("~/git/aoc-inputs/2015/2015_11.txt")
		assertEquals("hepxxyzz", Task11(input).a())
	}

	@Test
	fun b() {
		assertEquals("heqaabcc", Task11("hepxxyzz").a())
	}
}