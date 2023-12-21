package y2023

import Task
import utils.Grid
import utils.Point
import utils.readInputAsListOfStrings

object Task21:Task {

	override fun a(): Any {
		val input = readInputAsListOfStrings("2023_21.txt")
		return solveA(input, 64)
	}

	fun solveA(input: List<String>, steps: Int): Any {
		val grid = parseInputToGrid(input, Grid<Point>(input.size, input[0].length))
		return 0
	}

	private fun parseInputToGrid(input: List<String>, grid: Grid<Point>): Any {

	}

	override fun b(): Any {
		val input = readInputAsListOfStrings("2023_21.txt")
		return solveB(input)
	}

	fun solveB(input: List<String>): Any {
		return 0
	}


}