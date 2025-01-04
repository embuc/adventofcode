package y2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.readInputAsString
import kotlin.test.Ignore

class Task9Test {

	@Ignore
	@Test
	fun a_testSTrings() {
        assertEquals("ADVENT", Task9("ADVENT").a())
		assertEquals("ABBBBBC", Task9("A(1x5)BC").a())
		assertEquals("XYZXYZXYZ", Task9("(3x3)XYZ").a())
		assertEquals("ABCBCDEFEFG", Task9("A(2x2)BCD(2x2)EFG").a())
		assertEquals("(1x3)A", Task9("(6x1)(1x3)A").a())
		assertEquals("X(3x3)ABC(3x3)ABCY", Task9("X(8x2)(3x3)ABCY").a())
	}

	@Ignore
	@Test
	fun b_testStrings() {
		assertEquals("XYZXYZXYZ".length.toLong(), Task9("(3x3)XYZ").b())
		assertEquals("XABCABCABCABCABCABCY".length.toLong(), Task9("X(8x2)(3x3)ABCY").b())
		assertEquals(241920L, Task9("(27x12)(20x12)(13x14)(7x10)(1x12)A").b())
		assertEquals(445L, Task9("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN").b())
	}

	@Test
	fun a() {
		val input = readInputAsString("~/git/aoc-inputs/2016/2016_9.txt")
		assertEquals(110346, Task9(input).a().length)
	}

	@Test
	fun b() {
		val input = readInputAsString("~/git/aoc-inputs/2016/2016_9.txt")
		assertEquals(10774309173L, Task9(input).b())
	}
}