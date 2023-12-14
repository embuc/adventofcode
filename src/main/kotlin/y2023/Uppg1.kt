package y2023

import Task
import utils.getLinesFromFile

class Uppg1: Task {

	override fun a(): Any {
		return -1
	}

	override fun b(): Any {
		val lines = getLinesFromFile("Input1.txt")
		var sum = 0
		lines.forEach { line ->
			val (firstNumber, lastNumber) = Uppg1to4.extractFirstAndLastDigit(line)
			val combinedAsString = "$firstNumber$lastNumber"
			val combinedAsInt = combinedAsString.toIntOrNull() ?: 0
			sum+=combinedAsInt
		}
		return sum
	}
}