package y2023

import Task
import utils.readInputAsListOfStrings

object Task1: Task {

	override fun a(): Any {
		val lines = readInputAsListOfStrings("2023_1.txt")
		var sum = 0
		lines.forEach { line ->
			val (firstNumber, lastNumber) = extractFirstAndLastNumber(line)
			val combinedAsString = "$firstNumber$lastNumber"
			val combinedAsInt = combinedAsString.toIntOrNull() ?: 0
			println(line)
			println(combinedAsInt)
			sum+=combinedAsInt
		}
		return sum
	}

	override fun b(): Any {
		val lines = readInputAsListOfStrings("2023_1.txt")
		var sum = 0
		lines.forEach { line ->
			val (firstNumber, lastNumber) = extractFirstAndLastDigit(line)
			val combinedAsString = "$firstNumber$lastNumber"
			val combinedAsInt = combinedAsString.toIntOrNull() ?: 0
			sum+=combinedAsInt
		}
		return sum
	}

	fun extractFirstAndLastNumber(input: String): Pair<Int?, Int?> {
		val numberRegex = "\\d".toRegex()
		val matches = numberRegex.findAll(input).map { it.value.toInt() }.toList()

		val firstNumber = matches.firstOrNull()
		val lastNumber = matches.lastOrNull()

		return Pair(firstNumber, lastNumber)
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
}