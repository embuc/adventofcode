package y2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Task14Test {

	@Test
	fun a() {
//		assertEquals(22728, Task14("abc").a())
		assertEquals(23769, Task14("cuanljph").a())
	}

	@Test
	fun b() {
//		assertEquals(22551, Task14("abc").b())
		assertEquals(20606, Task14("cuanljph").b())
		//3 min 5sec for string version, 9 sec for bytearray with hex conversion, and 900ms with multithreading
	}
}