package y2015

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Task20Test {

	@Test
	fun a() {
		assertEquals(786240, Task20(34_000_000).a())
	}

	@Test
	fun b() {
		assertEquals(831600, Task20(34_000_000).b())
	}
}