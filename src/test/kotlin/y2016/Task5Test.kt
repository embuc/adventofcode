package y2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.Ignore

// comment ignore annotation to run tests, they are slowish
class Task5Test {

	@Ignore
	@Test
	fun a() {
		val input = "wtnhxymk"
		 assertEquals("2414bc77", Task5(input).a())
	}

	@Ignore
	@Test
	fun b() {
		val input = "wtnhxymk"
		assertEquals("437e60fc", Task5(input).b())
	}
}