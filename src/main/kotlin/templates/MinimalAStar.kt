package templates

import utils.manhattanDistance
import java.util.*

fun getNeighbors(gridWidth: Int, gridHeight: Int, pos: Pair<Int, Int>): List<Pair<Int, Int>> {
	val (row, col) = pos
	val neighbors = mutableListOf<Pair<Int, Int>>()
	val directions = listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0)) // Right, left, down, up

	for ((dr, dc) in directions) {
		val nr = row + dr
		val nc = col + dc
		if (nr in 0..<gridHeight && nc >= 0 && nc < gridWidth) {
			neighbors.add(Pair(nr, nc))
		}
	}
	return neighbors
}

fun reconstructPath(cameFrom: MutableMap<Pair<Int, Int>, Pair<Int, Int>>, current: Pair<Int, Int>): List<Pair<Int, Int>> {
	val path = mutableListOf<Pair<Int, Int>>()
	var currentPath = current

	while (cameFrom.containsKey(currentPath)) {
		path.add(currentPath)
		currentPath = cameFrom[currentPath]!!
	}
	path.add(currentPath)
	return path.reversed()
}

// A* algorithm
fun aStarSimple(gridWidth: Int, gridHeight: Int, start: Pair<Int, Int>, target: Pair<Int, Int>): List<Pair<Int, Int>>? {

	val openSet = PriorityQueue<Pair<Int, Pair<Int, Int>>>(compareBy { it.first })
	openSet.add(Pair(manhattanDistance(start, target), start))

	val cameFrom = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>() // Map of child to parent to recreate the path
	val costSoFar = mutableMapOf(start to 0) // Cost from start to current node

	while(openSet.isNotEmpty()) {
		val (_, current) = openSet.poll() // current position with lowest fScore

		if (current == target) {
			return reconstructPath(cameFrom, current)
		}

		for (neighbor in getNeighbors(gridWidth, gridHeight, current)) {
			val newCost = costSoFar[current]!! + 1 // Cost is always 1 (in this case but might be something dynamic)

			if (!costSoFar.containsKey(neighbor) || newCost < costSoFar[neighbor]!!) {
				cameFrom[neighbor] = current // set parent of current neighbor to current node
				costSoFar[neighbor] = newCost // update the cost to reach current neighbor
				val priority = newCost + manhattanDistance(neighbor, target) // f = g + h
				openSet.add(Pair(priority, neighbor)) // add the neighbor with fScore to the openSet
			}
		}
	}

	return null // No path found
}

fun main() {
	val gridWidth = 5
	val gridHeight = 5
	val start = Pair(0, 0)
	val target = Pair(4, 4)

	val path = aStarSimple(gridWidth, gridHeight, start, target)

	if (path != null) {
		println("Path found: $path")
	} else {
		println("No path found.")
	}
}