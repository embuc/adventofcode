package y2017

import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.assertEquals

class Task16Test {

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_16.txt")
		assertEquals("lbdiomkhgcjanefp", Task16(input).a())
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2017/2017_16.txt")
		assertEquals("ejkflpgnamhdcboi", Task16(input).b())
	}
}