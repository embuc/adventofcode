fun main(args: Array<String>) {
	uppg2a();
}

fun uppg2a() {
//	which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
	val red = 12
	val green = 13
	val blue = 14

	val lines = getLinesFromFile("Input2.txt")

	var sum = 0
	lines.forEach { line ->
		println(line)
		println(parseGame(line))
		sum += checkGame(parseGame(line), red, green, blue)
	}
	println("Sum: $sum")
}

fun checkGame(game: Game, redLimit: Int, greenLimit: Int, blueLimit: Int): Int {
	game.turns.forEach { turn ->
		if (turn.red > redLimit || turn.green > greenLimit || turn.blue > blueLimit) {
			return 0 // Game is impossible
		}
	}
	return game.gameId // Game is possible
}

data class Turn(val red: Int = 0, val green: Int = 0, val blue: Int = 0)

data class Game(val gameId: Int, val turns: List<Turn>)

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


private fun getLinesFromFile(fileName: String): List<String> {
	val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)
		?: throw IllegalArgumentException("File not found")
	val lines = inputStream.bufferedReader().use { it.readLines() }
	return lines
}

fun uppg1b() {
	val lines = getLinesFromFile("Input1.txt")
	var sum = 0
	lines.forEach { line ->
		val (firstNumber, lastNumber) = extractFirstAndLastDigit(line)
		val combinedAsString = "$firstNumber$lastNumber"
		val combinedAsInt = combinedAsString.toIntOrNull() ?: 0
		sum+=combinedAsInt
	}
	println("Sum: $sum")
}

fun extractFirstAndLastDigit(input: String): Pair<Int?, Int?> {
	val digitMap = mapOf(
		"one" to 1, "two" to 2, "three" to 3, "four" to 4,
		"five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9
	)
	val reversedDigitMap = digitMap.mapKeys { it.key.reversed() }
	println(reversedDigitMap)

	val regexPattern = "(one|two|three|four|five|six|seven|eight|nine|\\d)"
	val regexPatternReversed = "(enin|thgie|neves|xis|evif|ruof|eerht|owt|eno|\\d)"
	val regex = Regex(regexPattern)
	val regexReversed = Regex(regexPatternReversed)

	// Find the first digit or digit word
	val firstMatch = regex.find(input)?.value
	val firstDigit = firstMatch?.toIntOrNull() ?: digitMap[firstMatch]

	// Reverse the input to find the last digit or digit word
	val reversedInput = input.reversed()
	val lastMatch = regexReversed.find(reversedInput)?.value
	val lastDigit = lastMatch?.toIntOrNull() ?: reversedDigitMap[lastMatch]

	return Pair(firstDigit, lastDigit)
}
