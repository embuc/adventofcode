import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import y2023.Uppg1to4
import java.util.stream.Stream

class Uppg1to4Tests {
	companion object {
		@JvmStatic
		fun gameData(): Stream<Arguments> {
			return Stream.of(
				Arguments.of(
					"Game 81: 11 red, 8 blue, 1 green; 12 blue, 2 green, 14 red; 16 red, 2 green, 6 blue; 17 red, 2 green; 3 green, 3 blue, 15 red",
					Uppg1to4.Companion.Game(81, listOf(
						Uppg1to4.Companion.Turn(11, 1, 8), Uppg1to4.Companion.Turn(14, 2, 12), Uppg1to4.Companion.Turn(16, 2, 6), Uppg1to4.Companion.Turn(17, 2, 0), Uppg1to4.Companion.Turn(15, 3, 3)
					))
				),
				Arguments.of(
					"Game 82: 13 red, 1 blue, 6 green; 3 green, 12 red, 3 blue; 5 red, 3 green, 18 blue; 15 blue, 8 red",
					Uppg1to4.Companion.Game(82, listOf(
						Uppg1to4.Companion.Turn(13, 6, 1), Uppg1to4.Companion.Turn(12, 3, 3),
						Uppg1to4.Companion.Turn(5, 3, 18), Uppg1to4.Companion.Turn(8, 0, 15)
					))
				),
				Arguments.of(
					"Game 83: 9 green, 5 blue, 5 red; 8 green, 15 blue, 7 red; 4 green, 6 red, 10 blue; 5 green, 2 red",
					Uppg1to4.Companion.Game(83, listOf(
						Uppg1to4.Companion.Turn(5, 9, 5), Uppg1to4.Companion.Turn(7, 8, 15), Uppg1to4.Companion.Turn(6, 4, 10), Uppg1to4.Companion.Turn(2, 5, 0)
					))
				),
				Arguments.of(
					"Game 84: 2 blue, 2 green, 6 red; 2 green, 7 red, 1 blue; 3 green, 3 blue; 2 green, 3 red, 3 blue; 6 green, 4 red",
					Uppg1to4.Companion.Game(84, listOf(
						Uppg1to4.Companion.Turn(6, 2, 2), Uppg1to4.Companion.Turn(7, 2, 1), Uppg1to4.Companion.Turn(0, 3, 3), Uppg1to4.Companion.Turn(3, 2, 3), Uppg1to4.Companion.Turn(4, 6, 0)
					))
				),
				Arguments.of(
					"Game 85: 1 blue, 3 green, 5 red; 2 green, 2 red; 4 red, 3 blue; 2 green, 3 blue, 1 red; 4 red, 2 green, 4 blue",
					Uppg1to4.Companion.Game(85, listOf(
						Uppg1to4.Companion.Turn(5, 3, 1), Uppg1to4.Companion.Turn(2, 2, 0), Uppg1to4.Companion.Turn(4, 0, 3), Uppg1to4.Companion.Turn(1, 2, 3), Uppg1to4.Companion.Turn(4, 2, 4)
					))
				),
				Arguments.of(
					"Game 86: 6 red, 1 blue; 1 green, 16 red; 2 green, 1 red; 12 red, 1 blue",
					Uppg1to4.Companion.Game(86, listOf(
						Uppg1to4.Companion.Turn(6, 0, 1), Uppg1to4.Companion.Turn(16, 1, 0), Uppg1to4.Companion.Turn(1, 2, 0), Uppg1to4.Companion.Turn(12, 0, 1)
					))
				)
			)
		}
	}

	@ParameterizedTest
	@MethodSource("gameData")
	fun testGameParsing(input: String, expected: Uppg1to4.Companion.Game) {
		println(input)
		val result = Uppg1to4.parseGame(input)
		Assertions.assertEquals(expected, result)
	}

	@Test
	fun parseGameTest() {
		val input = "Game 1: 12 red, 2 green, 1 blue; 13 green, 2 red; 14 blue, 4 green, 3 red; 8 blue"
		val game = Uppg1to4.parseGame(input)
		Assertions.assertEquals(1, game.gameId)
		Assertions.assertEquals(4, game.turns.size)

		Assertions.assertEquals(12, game.turns[0].red)
		Assertions.assertEquals(2, game.turns[0].green)
		Assertions.assertEquals(1, game.turns[0].blue)

		Assertions.assertEquals(13, game.turns[1].green)
		Assertions.assertEquals(2, game.turns[1].red)
		Assertions.assertEquals(0, game.turns[1].blue)

		Assertions.assertEquals(14, game.turns[2].blue)
		Assertions.assertEquals(4, game.turns[2].green)
		Assertions.assertEquals(3, game.turns[2].red)

		Assertions.assertEquals(8, game.turns[3].blue)
		Assertions.assertEquals(0, game.turns[3].red)
		Assertions.assertEquals(0, game.turns[3].green)
	}

	@Test
	fun extractFirstAndLastDigit() {
		val input = "one two three four five six seven eight nine"
		val (firstDigit, lastDigit) = Uppg1to4.extractFirstAndLastDigit(input)
		Assertions.assertEquals(1, firstDigit)
		Assertions.assertEquals(9, lastDigit)
	}

	@Test
	fun extractFirstAndLastDigitOverlaping() {
		val input = "dc572twonejgl"
		val (firstDigit, lastDigit) = Uppg1to4.extractFirstAndLastDigit(input)
		Assertions.assertEquals(5, firstDigit)
		Assertions.assertEquals(1, lastDigit)
	}

	@Test
	fun extractFirstAndLastDigitOverlaping2() {
		val input = "dtwonec572twone7jgl"
		val (firstDigit, lastDigit) = Uppg1to4.extractFirstAndLastDigit(input)
		Assertions.assertEquals(2, firstDigit)
		Assertions.assertEquals(7, lastDigit)
	}
}