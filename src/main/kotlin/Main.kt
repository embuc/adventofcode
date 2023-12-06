fun main(args: Array<String>) {
	uppg4b();
}

fun uppg4b() {
	val lines = getLinesFromFile("Input4.txt")

	val copies = getCopies(lines)
	println(copies.map { it.value }.sum())
}

fun getCopies(lines: List<String>): MutableMap<Int, Int> {
	val cards = lines.map { parseCardString(it) }

	val copies: MutableMap<Int, Int> = mutableMapOf()

	for (card in cards) {
		copies[card.counter] = 1
	}
	for (card in cards) {
		println(card)
		val score = calculateCardScore2(card)
		for (i in 1..score) {
			println("Adding $i to ${card.counter}")
			copies[card.counter + i] = copies[card.counter + i]!! + copies[card.counter]!!
		}
	}

	for (copy in copies) {
		println("Copy: ${copy.key} Count: ${copy.value}")
	}
	return copies
}

fun uppg4a() {
	val lines = getLinesFromFile("Input4.txt")
	val cards = lines.map { parseCardString(it) }
	var sum = 0
	for (card in cards) {
		println(card)
		val score = calculateCardScore(card)
		println("Score: $score")
		sum += score
	}
	println("Sum: $sum")

}

fun calculateCardScore2(card: Card): Int {
	var score = 0
	var matchedCount = 0 // Count of matched numbers

	val matching = mutableListOf<Int>();
	for (number in card.lotteryNumbers) {
		if (number in card.winningNumbers) {
			matchedCount++
			if (matchedCount > 2) {
				score *= 2 // Double the score from the third match onwards
			} else {
				score += 1
			}
			matching.add(number)
		}
	}
	println(matching)
	return matching.size
}

fun calculateCardScore(card: Card): Int {
	var score = 0
	var matchedCount = 0 // Count of matched numbers

	val matching = mutableListOf<Int>();
	for (number in card.lotteryNumbers) {
		if (number in card.winningNumbers) {
			matchedCount++
			if (matchedCount > 2) {
				score *= 2 // Double the score from the third match onwards
			} else {
				score += 1
			}
			matching.add(number)
		}
	}
	println(matching)
	return score
}

data class Card(val counter: Int, val winningNumbers: List<Int>, val lotteryNumbers: List<Int>)

fun parseCardString(cardString: String): Card {
	val parts = cardString.split(":")
	val counter = parts[0].trim().substringAfter("Card").trim().toInt()

	val numbers = parts[1].split("|").map { it.trim() }
	val winningNumbers = numbers[0].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
	val lotteryNumbers = numbers[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }

	return Card(counter, winningNumbers, lotteryNumbers)
}

fun uppg3b() {
	val matrix = getLinesFromFile("Input3.txt").map { it.toCharArray().toTypedArray() }.toTypedArray()
	val products = findProducts(matrix)

	val totalProduct = products.sum()
	println("Total Product: $totalProduct")
}

fun findProducts(matrix: Array<Array<Char>>): MutableList<Long> {
	val products = mutableListOf<Long>()
	val usedNumbers = mutableSetOf<Pair<Int, Int>>() // Track used numbers by their starting coordinates

	for (row in matrix.indices) {
		for (col in matrix[row].indices) {
			if (matrix[row][col] == '*') {
				val product = getProductOfAdjacentNumbers(matrix, row, col, usedNumbers)
				if (product > 0) {
					products.add(product)
				}
			}
		}
	}
	return products
}

fun getProductOfAdjacentNumbers(matrix: Array<Array<Char>>, starRow: Int, starCol: Int, usedNumbers: MutableSet<Pair<Int, Int>>): Long {
	val directions = arrayOf(
		-1 to 0, 1 to 0, 0 to -1, 0 to 1,  // Up, Down, Left, Right
		-1 to -1, -1 to 1, 1 to -1, 1 to 1 // Diagonals
	)
	var product: Long = 1
	var numbersFound = 0

	for ((dr, dc) in directions) {
		val newRow = starRow + dr
		val newCol = starCol + dc
		if (newRow in matrix.indices && newCol in matrix[0].indices && matrix[newRow][newCol].isDigit()) {
			val startCoords = getStartCoordinates(matrix, newRow, newCol)
			if (startCoords !in usedNumbers) {
				val (number, _) = extractNumber(matrix, newRow, newCol)
				if (number.isNotBlank()) {
					if(numbersFound == 1) {
						println("Found $product and $number at $startCoords which produce product: ${product * number.toLong()}") // Debug
					}
					product *= number.toLong()
					numbersFound++
					usedNumbers.add(startCoords)
				}
			}
		}
	}

	return if (numbersFound > 1) product else 0 // Return product only if more than one number is found
}

fun getStartCoordinates(matrix: Array<Array<Char>>, row: Int, col: Int): Pair<Int, Int> {
	var currentCol = col
	while (currentCol > 0 && matrix[row][currentCol - 1].isDigit()) {
		currentCol--
	}
	return row to currentCol
}

fun uppg3a() {
	val lines = getLinesFromFile("Input3.txt")
	val matrix = lines.map { it.toCharArray().toTypedArray() }.toTypedArray()

	val validNumbers = findValidNumbers(matrix)

	println("Valid Numbers: $validNumbers")
	val sum = validNumbers.sumOf { it.toInt() }
	println("Sum: $sum")
}

fun extractNumber(matrix: Array<Array<Char>>, row: Int, col: Int): Pair<String, Int> {
	var startCol = col

	// Move left to the start of the number
	while (startCol > 0 && matrix[row][startCol - 1].isDigit()) {
		startCol--
	}

	val sb = StringBuilder()
	var currentCol = startCol

	// Extract the entire number moving right
	while (currentCol < matrix[row].size && matrix[row][currentCol].isDigit()) {
		sb.append(matrix[row][currentCol])
		currentCol++
	}

	return sb.toString() to currentCol // Return the number and the column index after the last digit
}

//fun extractNumber(matrix: Array<Array<Char>>, row: Int, col: Int): Pair<String, Int> {
//	if (col > 0 && matrix[row][col - 1].isDigit()) {
//		return "" to col // Already part of a processed number, skip
//	}
//	val sb = StringBuilder()
//	var currentCol = col
//	while (currentCol < matrix[row].size && matrix[row][currentCol].isDigit()) {
//		sb.append(matrix[row][currentCol])
//		currentCol++
//	}
//	return sb.toString() to currentCol // Return the number and the ending column index
//}

fun findValidNumbers(matrix: Array<Array<Char>>): List<String> {
	val validNumbers = mutableListOf<String>()

	for (row in matrix.indices) {
		var col = 0
		while (col < matrix[row].size) {
			if (matrix[row][col].isDigit()) {
				val (number, nextCol) = extractNumber(matrix, row, col)
				if (number.isNotBlank() && isNumberValid(matrix, row, col, nextCol)) {
					validNumbers.add(number)
				}
				col = nextCol
			} else {
				col++
			}
		}
	}
	return validNumbers
}

fun isNumberValid(matrix: Array<Array<Char>>, row: Int, startCol: Int, endCol: Int): Boolean {
	for (col in startCol until endCol) {
		if (isAdjacentToSymbol(matrix, row, col)) {
			return true
		}
	}
	return false
}

fun isAdjacentToSymbol(matrix: Array<Array<Char>>, row: Int, col: Int): Boolean {
	val directions = arrayOf(
		-1 to 0, 1 to 0, 0 to -1, 0 to 1,  // Up, Down, Left, Right
		-1 to -1, -1 to 1, 1 to -1, 1 to 1 // Diagonals
	)
	val validChars = charArrayOf('.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '\n').toList()

	for ((dr, dc) in directions) {
		val newRow = row + dr
		val newCol = col + dc
		if (newRow in matrix.indices && newCol in matrix[0].indices) {
			val adjacentChar = matrix[newRow][newCol]
			if (adjacentChar !in validChars) {
				return true
			}
		}
	}
	return false
}



fun uppg2b() {
	val lines = getLinesFromFile("Input2.txt")
	var sum = 0
	lines.forEach { line ->
		println(line)
		println(parseGame(line))
		sum += calculatePower(calculateMinimumCubes(parseGame(line)))
	}
	println("Sum: $sum")
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

fun calculateMinimumCubes(game: Game): Triple<Int, Int, Int> {
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

fun calculatePower(cubeSet: Triple<Int, Int, Int>): Int {
	return cubeSet.first * cubeSet.second * cubeSet.third
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
