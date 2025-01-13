package y2017

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Task3Test {

	@Test
	fun a() {
		assertEquals(419, Task3(289326).a())
	}

	@Test
	fun b() {
		assertEquals(295229, Task3(289326).b())
	}
}