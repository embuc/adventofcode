import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class MainKtTest {


	companion object {
		@JvmStatic
		fun gameData(): Stream<Arguments> {
			return Stream.of(
				Arguments.of(
					"Game 81: 11 red, 8 blue, 1 green; 12 blue, 2 green, 14 red; 16 red, 2 green, 6 blue; 17 red, 2 green; 3 green, 3 blue, 15 red",
					Game(81, listOf(
						Turn(11, 1, 8), Turn(14, 2, 12), Turn(16, 2, 6), Turn(17, 2, 0), Turn(15, 3, 3)
					))
				),
				Arguments.of(
					"Game 82: 13 red, 1 blue, 6 green; 3 green, 12 red, 3 blue; 5 red, 3 green, 18 blue; 15 blue, 8 red",
					Game(82, listOf(
						Turn(13, 6, 1), Turn(12, 3, 3), Turn(5, 3, 18), Turn(8, 0, 15)
					))
				),
				Arguments.of(
					"Game 83: 9 green, 5 blue, 5 red; 8 green, 15 blue, 7 red; 4 green, 6 red, 10 blue; 5 green, 2 red",
					Game(83, listOf(
						Turn(5, 9, 5), Turn(7, 8, 15), Turn(6, 4, 10), Turn(2, 5, 0)
					))
				),
				Arguments.of(
					"Game 84: 2 blue, 2 green, 6 red; 2 green, 7 red, 1 blue; 3 green, 3 blue; 2 green, 3 red, 3 blue; 6 green, 4 red",
					Game(84, listOf(
						Turn(6, 2, 2), Turn(7, 2, 1), Turn(0, 3, 3), Turn(3, 2, 3), Turn(4, 6, 0)
					))
				),
				Arguments.of(
					"Game 85: 1 blue, 3 green, 5 red; 2 green, 2 red; 4 red, 3 blue; 2 green, 3 blue, 1 red; 4 red, 2 green, 4 blue",
					Game(85, listOf(
						Turn(5, 3, 1), Turn(2, 2, 0), Turn(4, 0, 3), Turn(1, 2, 3), Turn(4, 2, 4)
					))
				),
				Arguments.of(
					"Game 86: 6 red, 1 blue; 1 green, 16 red; 2 green, 1 red; 12 red, 1 blue",
					Game(86, listOf(
						Turn(6, 0, 1), Turn(16, 1, 0), Turn(1, 2, 0), Turn(12, 0, 1)
					))
				)
			)
		}
	}

	@ParameterizedTest
	@MethodSource("gameData")
	fun testGameParsing(input: String, expected: Game) {
		println(input)
		val result = parseGame(input)
		assertEquals(expected, result)
	}

	@Test
	fun parseGame() {
		val input = "Game 1: 12 red, 2 green, 1 blue; 13 green, 2 red; 14 blue, 4 green, 3 red; 8 blue"
		val game = parseGame(input)
		assertEquals(1, game.gameId)
		assertEquals(4, game.turns.size)

		assertEquals(12, game.turns[0].red)
		assertEquals(2, game.turns[0].green)
		assertEquals(1, game.turns[0].blue)

		assertEquals(13, game.turns[1].green)
		assertEquals(2, game.turns[1].red)
		assertEquals(0, game.turns[1].blue)

		assertEquals(14, game.turns[2].blue)
		assertEquals(4, game.turns[2].green)
		assertEquals(3, game.turns[2].red)

		assertEquals(8, game.turns[3].blue)
		assertEquals(0, game.turns[3].red)
		assertEquals(0, game.turns[3].green)
	}

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