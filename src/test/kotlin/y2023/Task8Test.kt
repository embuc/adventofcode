package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Task8Test {

	@Test
	fun a() {
		assertEquals(16271, Task8.a())
	}

	@Test
	fun b() {
		assertEquals(14265111103729, Task8.b())
	}
}