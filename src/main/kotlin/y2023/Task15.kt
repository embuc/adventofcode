package y2023

import Task
import utils.readInput

object Task15: Task {

	override fun a(): Any {
		val strings = readInput("2023_15.txt").split(",")
		return solveA(strings)
	}

	override fun b(): Any {
		return 0
	}

//	Determine the ASCII code for the current character of the string.
//	Increase the current value by the ASCII code you just determined.
//	Set the current value to itself multiplied by 17.
//	Set the current value to the remainder of dividing itself by 256.
	fun solveA(list: List<String>): Long {
		return list.sumOf { s ->
			s.toList().fold(0L) { prevResult, currChar -> ((prevResult + currChar.code) * 17) % 256 }
		}
}
}