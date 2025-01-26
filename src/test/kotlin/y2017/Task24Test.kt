package y2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Task24Test {

	val testInput =
		"""
		0/2
        2/2
        2/3
        3/4
        3/5
        0/1
        10/1
        9/10
		""".trim().lines().map{it.trim()}
	@Test
	fun a() {
		assertEquals(31, Task24(testInput).a())

	}

	@Test
	fun b() {
	}
}