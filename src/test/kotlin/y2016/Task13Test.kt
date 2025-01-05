package y2016

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Task13Test {

	@Test
	fun a() {
		assertEquals(11, Task13(10, 4, 7).a())
		assertEquals(90, Task13(1352, 39, 31).a())
	}

	@Test
	fun b() {
		assertEquals(135, Task13(1352, 39, 31).b())
	}
}