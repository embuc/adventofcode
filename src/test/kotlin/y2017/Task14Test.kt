package y2017

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Task14Test {

	@Test
	fun a() {
		val input = "stpzcrnm"
		assertEquals(8250, Task14(input).a())
	}

	@Test
	fun b() {
		val input = "stpzcrnm"
		assertEquals(1113, Task14(input).b())
	}
}