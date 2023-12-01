import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class MainKtTest {

	@Test
	fun extractFirstAndLastDigit() {
		val input = "one two three four five six seven eight nine"
		val (firstDigit, lastDigit) = extractFirstAndLastDigit(input)
		assertEquals(1, firstDigit)
		assertEquals(9, lastDigit)
	}

	@Test
	fun extractFirstAndLastDigitOverlaping() {
		val input = "dc572twonejgl"
		val (firstDigit, lastDigit) = extractFirstAndLastDigit(input)
		assertEquals(5, firstDigit)
		assertEquals(1, lastDigit)
	}

	@Test
	fun extractFirstAndLastDigitOverlaping2() {
		val input = "dtwonec572twone7jgl"
		val (firstDigit, lastDigit) = extractFirstAndLastDigit(input)
		assertEquals(2, firstDigit)
		assertEquals(7, lastDigit)
	}
}