package y2024

import Task
import utils.toGrid

/*--- Day 12: Garden Groups ---
* There's a more efficient way to solve part II, specifically - calculating corners. However, this was the first
* approach that came to mind, and it worked reasonably well and quickly. One thing to note, though, is that I had to
* sort the groups first—if the tiles were checked in a random order, it would fail. */
class Task12(val input: List<String>) : Task {

	override fun a(): Any {
		val grid = toGrid(input)
		val groups = findGroups(grid)
		return countCost(groups)
	}

	private fun countCost(groups: List<List<Triple<Int, Int, Char>>>): Int {
		return groups.sumOf { group ->
			val groupSet = group.toSet() // Use a set for fast membership checks
			val area = group.size // The number of cells in the group

			// Calculate perimeter
			val perimeter = group.sumOf { (x, y, c) ->
				listOf(
					x - 1 to y, // Up
					x + 1 to y, // Down
					x to y - 1, // Left
					x to y + 1  // Right
				).count { (nx, ny) ->
					// Check if the neighbor is not in the groupSet
					groupSet.none { it.first == nx && it.second == ny } ||
							groupSet.firstOrNull { it.first == nx && it.second == ny }?.third != c
				}
			}

			// Calculate inner perimeter
			val innerPerimeter = group.sumOf { (x, y, c) ->
				listOf(
					x - 1 to y, // Up
					x + 1 to y, // Down
					x to y - 1, // Left
					x to y + 1  // Right
				).count { (nx, ny) ->
					// Check if the neighbor is in the groupSet and has a different character
					groupSet.any { it.first == nx && it.second == ny } &&
							groupSet.firstOrNull { it.first == nx && it.second == ny }?.third != c
				}
			}

			// Calculate cost
			val cost = area * (perimeter - innerPerimeter)

			// Debugging information
//			println("Group: $group")
//			println("Area: $area")
//			println("Perimeter: $perimeter")
//			println("Inner Perimeter: $innerPerimeter")
//			println("Cost: $cost")
			cost
		}
	}

	private fun visualizeGroups(
		grid: Array<Array<Triple<Int, Int, Char>>>,
		groups: List<List<Triple<Int, Int, Char>>>
	) {
		groups.map { group -> visualizeGroup(grid, group) }.forEach {
			println()
			printGrid(it)
		}
	}

	private fun visualizeGroup(
		grid: Array<Array<Triple<Int, Int, Char>>>,
		group: List<Triple<Int, Int, Char>>
	): Array<Array<Char>> {
		val groupGrid = Array(grid.size) { Array(grid[0].size) { '.' } }
		group.forEach { (x, y, c) -> groupGrid[x][y] = c }
		return groupGrid
	}

	private fun printGrid(grid: Array<Array<Char>>) {
		grid.forEach { println(it.joinToString("")) }
	}

	private fun findGroups(grid: Array<Array<Triple<Int, Int, Char>>>): List<List<Triple<Int, Int, Char>>> {
		// check for adjecent up/down/right/left cells for same type of character and add to group
		// we need breadth first search, so we start with one tile and search for all adjecent tiles and also add them to 'seen'
		// we do not start new groups if we have already seen the tile
		val seen = mutableSetOf<Pair<Int, Int>>()
		val groups = mutableListOf<List<Triple<Int, Int, Char>>>()
		for (i in grid.indices) {
			for (j in grid[i].indices) {
				val curr = grid[i][j].third
				if (seen.contains(i to j)) continue
				val group = mutableListOf<Triple<Int, Int, Char>>()
				val queue = mutableListOf(i to j)
				while (queue.isNotEmpty()) {
					val (x, y) = queue.removeFirst()
					if (seen.contains(x to y)) continue
					seen.add(x to y)
					group.add(grid[x][y])
					// add adjecent cells to queue
					if (x - 1 >= 0 && curr == grid[x - 1][y].third) queue.add(x - 1 to y)
					if (x + 1 < grid.size && curr == grid[x + 1][y].third) queue.add(x + 1 to y)
					if (y - 1 >= 0 && curr == grid[x][y - 1].third) queue.add(x to y - 1)
					if (y + 1 < grid[x].size && curr == grid[x][y + 1].third) queue.add(x to y + 1)
				}
				groups.add(group)
			}
		}
		return groups

	}

	override fun b(): Any {
		val grid = toGrid(input)
		val groups = findGroups(grid)
		return countCostB(groups)
	}

	private fun countCostB(groups: List<List<Triple<Int, Int, Char>>>): Int {
		return groups.sumOf { group ->
			val groupSet = group.map { it.first to it.second }.toSet() // Convert group to a set of (x, y) coordinates
			val charMap = group.associate { (x, y, c) -> (x to y) to c } // Map (x, y) -> char for quick access

			var perimeter = 0
			val seenUp = mutableSetOf<Pair<Int, Int>>()
			val seenDown = mutableSetOf<Pair<Int, Int>>()
			val seenLeft = mutableSetOf<Pair<Int, Int>>()
			val seenRight = mutableSetOf<Pair<Int, Int>>()
			//need to sort group by x and y
			for ((x, y, c) in group.sortedWith(compareBy({ it.first }, { it.second }))) {
				// Check top edge
				if ((x - 1 to y) !in groupSet || charMap[x - 1 to y] != c) {
					seenUp.add(x to y)
					// Check neighbors to the left and right of the tile above
					val leftNeighbor = x to y - 1
					val rightNeighbor = x to y + 1

					if (leftNeighbor !in groupSet && rightNeighbor !in groupSet
						|| leftNeighbor !in seenUp && rightNeighbor !in seenUp
					) {
						perimeter++
					}
				}

				// Check bottom edge
				if ((x + 1 to y) !in groupSet || charMap[x + 1 to y] != c) {
					seenDown.add(x to y)
					val leftNeighbor = x to y - 1
					val rightNeighbor = x to y + 1
					if (leftNeighbor !in groupSet && rightNeighbor !in groupSet
						|| leftNeighbor !in seenDown && rightNeighbor !in seenDown
					) {
						perimeter++
					}
				}

				// Check left edge
				if ((x to y - 1) !in groupSet || charMap[x to y - 1] != c) {
					seenLeft.add(x to y)
					val topNeighbor = x - 1 to y
					val bottomNeighbor = x + 1 to y

					if (topNeighbor !in groupSet && bottomNeighbor !in groupSet
						|| topNeighbor !in seenLeft && bottomNeighbor !in seenLeft
					) {
						perimeter++
					}
				}

				// Check right edge
				if ((x to y + 1) !in groupSet || charMap[x to y + 1] != c) {
					seenRight.add(x to y)
					val topNeighbor = x - 1 to y
					val bottomNeighbor = x + 1 to y

					if (topNeighbor !in groupSet && bottomNeighbor !in groupSet
						|| topNeighbor !in seenRight && bottomNeighbor !in seenRight
					) {
						perimeter++
					}
				}
			}

			// Area is the number of tiles in the group
			val area = group.size

			val cost = area * (perimeter)

			// Debugging information
//			println("Group: $group")
//			println("Area: $area")
//			println("Perimeter: $perimeter")
//			println("Outer Perimeter: $outerPerimeter")
//			println("Inner Perimeter: $innerPerimeter")
//			println("Cost: $cost")

			cost
		}
	}

}
