package y2023

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Task1Test {

	val task = Task1

	@Test
	fun extractFirstAndLastDigit() {
		val input = "one two three four five six seven eight nine"
		val (firstDigit, lastDigit) = task.extractFirstAndLastDigit(input)
		Assertions.assertEquals(1, firstDigit)
		Assertions.assertEquals(9, lastDigit)
	}

	@Test
	fun extractFirstAndLastDigitOverlaping() {
		val input = "dc572twonejgl"
		val (firstDigit, lastDigit) = task.extractFirstAndLastDigit(input)
		Assertions.assertEquals(5, firstDigit)
		Assertions.assertEquals(1, lastDigit)
	}

	@Test
	fun extractFirstAndLastDigitOverlaping2() {
		val input = "dtwonec572twone7jgl"
		val (firstDigit, lastDigit) = task.extractFirstAndLastDigit(input)
		Assertions.assertEquals(2, firstDigit)
		Assertions.assertEquals(7, lastDigit)
	}
}