package y2024

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Task21Test {

    val test_input = """
	029A
	980A
	179A
	456A
	379A
	""".trimIndent().lines()

	@Test
	fun a() {
		assertEquals(126384, Task21(test_input).a())
	}

	@Test
	fun b() {
	}
}