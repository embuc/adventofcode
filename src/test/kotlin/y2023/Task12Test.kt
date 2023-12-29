package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Task12Test {

	@Test
	fun a() {
		assertEquals(7771, Task12.a())
	}

	@Test
	fun b() {
		assertEquals(10861030975833L, Task12.b())
	}
}