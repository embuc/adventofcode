package y2015

import Task

/*--- Day 3: Perfectly Spherical Houses in a Vacuum ---*/
class Task3(val input:String):Task {

	override fun a(): Any {
		val visited = mutableSetOf<Pair<Int, Int>>()
		var x = 0
		var y = 0
		visited.add(Pair(x, y))
		for (c in input) {
			when (c) {
				'>' -> x++
				'<' -> x--
				'^' -> y++
				'v' -> y--
			}
			visited.add(Pair(x, y))
		}
		return visited.size
	}

	override fun b(): Any {
		val visited = mutableSetOf<Pair<Int, Int>>()
		var x1 = 0
		var y1 = 0
		var x2 = 0
		var y2 = 0
		visited.add(Pair(x1, y1))
		for ((i, c) in input.withIndex()) {
			if (i % 2 == 0) {
				when (c) {
					'>' -> x1++
					'<' -> x1--
					'^' -> y1++
					'v' -> y1--
				}
				visited.add(Pair(x1, y1))
			} else {
				when (c) {
					'>' -> x2++
					'<' -> x2--
					'^' -> y2++
					'v' -> y2--
				}
				visited.add(Pair(x2, y2))
			}
		}
		return visited.size
	}
}