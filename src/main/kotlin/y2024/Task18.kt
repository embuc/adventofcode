package y2024

import Task
import templates.MinimalBFS

/*--- Day 18: RAM Run ---*/
class Task18(val input : List<String>, val fallen:Int):Task {

	override fun a(): Any {
		val list = mutableListOf<List<Int>>()
		for (line in input) {
			list.add(line.split(",").map {it.toInt()})
		}
		val (x, h) = findWidthAndHeight(list)
		val grid = Array(x) { CharArray(h) { '.' } }
		for (c in 0 until fallen) {
			val (i, j) = list[c]
			grid[j][i] = '#'
		}
		grid[0][0] = 'S'
		grid[x-1][h-1] = 'E'
//		printGrid(grid)

		// bfs path from 0, 0 to x, h
		val findShortestPath = MinimalBFS.findShortestPath(grid)
//		var i=1
//		for ((first, second) in findShortestPath) {
//			grid[first][second] = 'X'
//			println("Step $i: $first, $second")
//			i++
//		}
//		printGrid(grid)
		return findShortestPath.size -1 //it seems that the first step is not counted
	}

	private fun findWidthAndHeight(list: MutableList<List<Int>>): Pair<Int, Int> {
		var x = 0
		var h = 0
		for (l in list) {
			if (l[0] > x) {
				x = l[0]
			}
			if (l[1] > h) {
				h = l[1]
			}
		}
		return Pair(x+1, h+1)
	}

	override fun b(): String {
		val list = mutableListOf<List<Int>>()
		for (line in input) {
			list.add(line.split(",").map {it.toInt()})
		}
		val (x, h) = findWidthAndHeight(list)
		val grid = Array(x) { CharArray(h) { '.' } }
		for (c in 0 until fallen) {
			val (i, j) = list[c]
			grid[j][i] = '#'
		}
		grid[0][0] = 'S'
		grid[x-1][h-1] = 'E'
		for(c in fallen until list.size) {
			val (i, j) = list[c]
			grid[j][i] = '#'
			MinimalBFS.findShortestPath(grid) ?: return "$i,$j"
		}
		return ":/"
	}
}