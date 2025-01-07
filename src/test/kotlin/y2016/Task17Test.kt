package y2016

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Task17Test {

	@Test
	fun a() {
		assertEquals("DDRRUDLRRD", Task17("pslxynzg").a())
	}

	@Test
	fun b() {
		assertEquals(488, Task17("pslxynzg").b())
	}
}