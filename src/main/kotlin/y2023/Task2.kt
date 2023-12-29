package y2023

import Task
import utils.readInputAsListOfStrings

// --- Day 2: Cube Conundrum ---
object Task2: Task {

	override fun a(): Any {
		val red = 12
		val green = 13
		val blue = 14

		val lines = readInputAsListOfStrings("2023_2.txt")

		var sum = 0
		lines.forEach { line ->
			sum += checkGame(parseGame(line), red, green, blue)
		}
		return sum
	}

	override fun b(): Any {
		val lines = readInputAsListOfStrings("2023_2.txt")
		var sum = 0
		lines.forEach { line ->
			sum += calculatePower(calculateMinimumCubes(parseGame(line)))
		}
		return sum
	}

	private fun calculateMinimumCubes(game: Game): Triple<Int, Int, Int> {
		var maxRed = 0
		var maxGreen = 0
		var maxBlue = 0

		game.turns.forEach { turn ->
			maxRed = maxOf(maxRed, turn.red)
			maxGreen = maxOf(maxGreen, turn.green)
			maxBlue = maxOf(maxBlue, turn.blue)
		}

		return Triple(maxRed, maxGreen, maxBlue)
	}

	private fun calculatePower(cubeSet: Triple<Int, Int, Int>): Int {
		return cubeSet.first * cubeSet.second * cubeSet.third
	}

	fun parseGame(input: String): Game {
		val gameIdRegex = "Game (\\d+):".toRegex()
		val gameId = gameIdRegex.find(input)?.groups?.get(1)?.value?.toInt()
			?: throw IllegalArgumentException("Invalid game format")

		val colorRegex = "(\\d+) (red|green|blue)".toRegex()
		val turnSplits = input.substringAfter(":").split(";").map { it.trim() }

		val turns = turnSplits.map { turnStr ->
			val colors = colorRegex.findAll(turnStr).map {
				when (it.groups[2]!!.value) {
					"red" -> "red" to it.groups[1]!!.value.toInt()
					"green" -> "green" to it.groups[1]!!.value.toInt()
					"blue" -> "blue" to it.groups[1]!!.value.toInt()
					else -> throw IllegalArgumentException("Unexpected color")
				}
			}.toMap()

			Turn(colors["red"] ?: 0, colors["green"] ?: 0, colors["blue"] ?: 0)
		}

		return Game(gameId, turns)
	}

	private fun checkGame(game: Game, redLimit: Int, greenLimit: Int, blueLimit: Int): Int {
		game.turns.forEach { turn ->
			if (turn.red > redLimit || turn.green > greenLimit || turn.blue > blueLimit) {
				return 0 // Game is impossible
			}
		}
		return game.gameId // Game is possible
	}

	data class Turn(val red: Int = 0, val green: Int = 0, val blue: Int = 0)

	data class Game(val gameId: Int, val turns: List<Turn>)
}