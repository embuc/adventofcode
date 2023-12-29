package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Task9Test {

	@Test
	fun c() {
		val expected = listOf(5, 10, 13, 16, 21, 30, 45)
		assertEquals(expected, Task9.c())
	}

	@Test
	fun a() {
		assertEquals(1930746032, Task9.a())
	}

	@Test
	fun b() {
		assertEquals(1154, Task9.b())
	}
}