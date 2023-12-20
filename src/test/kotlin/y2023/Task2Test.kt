package y2023

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Task2Test {
	companion object {
		@JvmStatic
		fun gameData(): Stream<Arguments> {
			return Stream.of(
				Arguments.of(
					"Game 81: 11 red, 8 blue, 1 green; 12 blue, 2 green, 14 red; 16 red, 2 green, 6 blue; 17 red, 2 green; 3 green, 3 blue, 15 red",
					Task2.Game(81, listOf(
						Task2.Turn(11, 1, 8), Task2.Turn(14, 2, 12), Task2.Turn(16, 2, 6), Task2.Turn(17, 2, 0), Task2.Turn(15, 3, 3)
					))
				),
				Arguments.of(
					"Game 82: 13 red, 1 blue, 6 green; 3 green, 12 red, 3 blue; 5 red, 3 green, 18 blue; 15 blue, 8 red",
					Task2.Game(82, listOf(
						Task2.Turn(13, 6, 1), Task2.Turn(12, 3, 3),
						Task2.Turn(5, 3, 18), Task2.Turn(8, 0, 15)
					))
				),
				Arguments.of(
					"Game 83: 9 green, 5 blue, 5 red; 8 green, 15 blue, 7 red; 4 green, 6 red, 10 blue; 5 green, 2 red",
					Task2.Game(83, listOf(
						Task2.Turn(5, 9, 5), Task2.Turn(7, 8, 15), Task2.Turn(6, 4, 10), Task2.Turn(2, 5, 0)
					))
				),
				Arguments.of(
					"Game 84: 2 blue, 2 green, 6 red; 2 green, 7 red, 1 blue; 3 green, 3 blue; 2 green, 3 red, 3 blue; 6 green, 4 red",
					Task2.Game(84, listOf(
						Task2.Turn(6, 2, 2), Task2.Turn(7, 2, 1), Task2.Turn(0, 3, 3), Task2.Turn(3, 2, 3), Task2.Turn(4, 6, 0)
					))
				),
				Arguments.of(
					"Game 85: 1 blue, 3 green, 5 red; 2 green, 2 red; 4 red, 3 blue; 2 green, 3 blue, 1 red; 4 red, 2 green, 4 blue",
					Task2.Game(85, listOf(
						Task2.Turn(5, 3, 1), Task2.Turn(2, 2, 0), Task2.Turn(4, 0, 3), Task2.Turn(1, 2, 3), Task2.Turn(4, 2, 4)
					))
				),
				Arguments.of(
					"Game 86: 6 red, 1 blue; 1 green, 16 red; 2 green, 1 red; 12 red, 1 blue",
					Task2.Game(86, listOf(
						Task2.Turn(6, 0, 1), Task2.Turn(16, 1, 0), Task2.Turn(1, 2, 0), Task2.Turn(12, 0, 1)
					))
				)
			)
		}
	}

	@ParameterizedTest
	@MethodSource("gameData")
	fun testGameParsing(input: String, expected: Task2.Game) {
		println(input)
		val task = Task2()
		val result = task.parseGame(input)
		Assertions.assertEquals(expected, result)
	}

	@Test
	fun parseGameTest() {
		val task = Task2()
		val input = "Game 1: 12 red, 2 green, 1 blue; 13 green, 2 red; 14 blue, 4 green, 3 red; 8 blue"
		val game = task.parseGame(input)
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

}