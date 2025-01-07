package y2016

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Task16Test {

	@Test
	fun a() {
		assertEquals("01100", Task16("10000", 20).a())
		assertEquals("01110011101111011", Task16("11110010111001001", 272).a())
	}

	@Test
	fun b() {
		assertEquals("11001111011000111", Task16("11110010111001001", 35651584).a())
	}
}