package y2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Task19Test {

	@Test
	fun a() {
		assertEquals(3, Task19(5).a())
		assertEquals(5, Task19(10).a())
		assertEquals(7, Task19(11).a())
		assertEquals(1816277, Task19(3005290).a())
	}

	@Test
	fun b() {
//		assertEquals(2, Task19(5).b())
//		assertEquals(2, Task19(11).b())
//		assertEquals(5, Task19(14).b())
		assertEquals(1410967, Task19(3005290).b())
	}
}