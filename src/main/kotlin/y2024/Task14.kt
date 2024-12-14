package y2024

import Task

/*--- Day 14: Restroom Redoubt ---*/
class Task14(val input: List<String>) : Task {

	override fun a(): Long {
		// parse input into a list of pairs of positions and velocities
		// for each second, move each position by the velocity with % width and height respectively in order to wrap
		// around the grid
		// robots can be more than one on a tile
		// after 100 seconds chack 4 quadrnats (divided by the center of the grid) - thats why the grid is odd
		// of rows and columns, discard the middle part and any robots there
		// count the number of robots in each quadrant
		// multiply in all 4 quadrants and return the result
		//TODO optimization idea: get the position after X seconds: pos = (pos + time*velocity) mod width (or height)
		return a(101, 103)
	}

	fun a(width: Int, height: Int): Long {
		val robots = input.map { it ->
			val (p, v) = it.split(" ")
			val (px, py) = p.replace("p=", "").split(",").map { it.toInt() }
			val (vx, vy) = v.replace("v=", "").split(",").map { it.toInt() }
			Robot(px, py, vx, vy)
		}

		val grid = Array(height) { IntArray(width) }
		for (robot in robots) {
			grid[robot.y][robot.x]++
		}
		for (i in 0 until 100) {
			for (robot in robots) {
				grid[robot.y][robot.x]--
				val xn = (robot.x + robot.vx) % width
				val yn = (robot.y + robot.vy) % height

				robot.x = if (xn >= 0) xn else (xn + width)
				robot.y = if (yn >= 0) yn else (yn + height)
				grid[robot.y][robot.x]++
			}
		}

		val centerW = width / 2
		val centerH = height / 2
		val bottom = centerH + 1
		val right = centerW + 1

		val tl = grid.sliceArray(0 until centerH).map { it.sliceArray(0 until centerW) }.sumOf { it.sum() }
		val tr = grid.sliceArray(0 until centerH).map { it.sliceArray(right until width) }.sumOf { it.sum() }
		val bl = grid.sliceArray(bottom until height).map { it.sliceArray(0 until centerW) }.sumOf { it.sum() }
		val br = grid.sliceArray(bottom until height).map { it.sliceArray(right until width) }.sumOf { it.sum() }
		return tl * tr * bl * br.toLong()
	}

	fun b(width: Int, height: Int): Int {
		val robots = input.map { it ->
			val (p, v) = it.split(" ")
			val (px, py) = p.replace("p=", "").split(",").map { it.toInt() }
			val (vx, vy) = v.replace("v=", "").split(",").map { it.toInt() }
			Robot(px, py, vx, vy)
		}

		val grid = Array(height) { IntArray(width) }
		for (robot in robots) {
			grid[robot.y][robot.x]++
		}
		var seconds = 1
		while (true) {
			for (robot in robots) {
				grid[robot.y][robot.x]--
				val xn = (robot.x + robot.vx) % width
				val yn = (robot.y + robot.vy) % height

				robot.x = if (xn >= 0) xn else (xn + width)
				robot.y = if (yn >= 0) yn else (yn + height)
				grid[robot.y][robot.x]++
			}
			var full = true
			for (gi in grid.indices) {
				for (gj in grid[gi].indices) {
					if (grid[gi][gj] == 2) {
						full = false
						break
					}
				}
				if (!full) {
					break
				}
			}
			if(full) {
//				println()
//				printPurgedGrid(grid)
//				println()
				return seconds
			}
			seconds++
		}
	}

	private fun printPurgedGrid(grid: Array<IntArray>) {
		grid.forEach { row ->
			row.forEach { print(if (it != 0) it else ' ') }
			println()
		}
	}

	data class Robot(var x: Int, var y: Int, val vx: Int, val vy: Int)

	override fun b(): Int {
		return b(101, 103)
	}
}