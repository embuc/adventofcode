package y2023

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

class Task20Test {

	@Test
	fun a() {
		val actual = Task20.a()
		assertEquals(818649769, actual)
	}

	@Test
	fun b() {
		val actual = Task20.b()
		assertEquals(246313604784977L, actual)
	}

}