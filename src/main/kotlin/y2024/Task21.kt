package y2024

import Task
import java.util.*

//--- Day 21: Keypad Conundrum ---
class Task21(val input: List<String>) : Task {
//	+---+---+---+
//	| 7 | 8 | 9 |
//	+---+---+---+
//	| 4 | 5 | 6 |
//	+---+---+---+
//	| 1 | 2 | 3 |
//	+---+---+---+
//      | 0 | A |
//      +---+---+

//  	+---+---+
//  	| ^ | A |
//	+---+---+---+
//	| < | v | > |
//	+---+---+---+
//	<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A
//	v<<A>>^A<A>AvA<^AA>A<vAAA>^A
//	<A^A>^^AvvvA
//	029A
//	029A: <vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A
//	980A: <v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A
//	179A: <v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A
//	456A: <v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A
//	379A: <v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A

	// Keypads for all robots
	val numericKeypad = listOf(
		listOf('7', '8', '9'),
		listOf('4', '5', '6'),
		listOf('1', '2', '3'),
		listOf('#', '0', 'A')
	)

	val directionalKeypad = listOf(
		listOf('#', '^', 'A'),
		listOf('<', 'v', '>')
	)
	override fun a(): Any {
		//the strategy is to find the shortest path from the start to the end for me, then find the shortest path from the
		//start to the end for each robot, then calculate the complexity
//		val code = "029A"
		val code = "379A"

		var sum = 0
//		for (code in input) {


		val targetSequence = "029A"
		val (robot1Path_1, robot2Path_1, robot3Path_1) = findShortestCombinedPath(numericKeypad, directionalKeypad, targetSequence)

		println("Robot 1 Path: $robot1Path_1 (Length: ${robot1Path_1.length})")
		println("Robot 2 Path: $robot2Path_1 (Length: ${robot2Path_1.length})")
		println("Robot 3 Path: $robot3Path_1 (Length: ${robot3Path_1.length})")
		println("Total Combined Path Length: ${robot1Path_1.length + robot2Path_1.length + robot3Path_1.length}")




//		}
		return sum
	}


	fun findShortestCombinedPath(
		numericalKeypad: List<List<Char>>,
		directionalKeypad: List<List<Char>>,
		targetSequence: String
	): Triple<String, String, String> {
		// Find all shortest paths for Robot 1 on the numerical keypad
		val robot1Start = findKeyPosition(numericalKeypad, 'A')
		val robot1Paths = bfsAllShortestPaths(numericalKeypad, robot1Start, targetSequence)

		var minTotalLength = Int.MAX_VALUE
		var bestRobot1Path = ""
		var bestRobot2Path = ""
		var bestRobot3Path = ""

		// Explore all Robot 1 paths
		for (robot1Path in robot1Paths) {
			// Find all shortest paths for Robot 2 on the directional keypad
			val robot2Start = findKeyPosition(directionalKeypad, 'A')
			val robot2Paths = bfsAllShortestPaths(directionalKeypad, robot2Start, robot1Path)

			for (robot2Path in robot2Paths) {
				// Find all shortest paths for Robot 3 on the directional keypad
				val robot3Start = findKeyPosition(directionalKeypad, 'A')
				val robot3Paths = bfsAllShortestPaths(directionalKeypad, robot3Start, robot2Path)

				for (robot3Path in robot3Paths) {
					// Calculate the total length of the combined path
					val totalLength = robot1Path.length + robot2Path.length + robot3Path.length

//					if(bestRobot3Path.length == 68) {
//						println("Robot 1 Path: $robot1Path (Length: ${robot1Path.length})")
//						println("Robot 2 Path: $robot2Path (Length: ${robot2Path.length})")
//						println("Robot 3 Path: $robot3Path (Length: ${robot3Path.length})")
//						println("Total Combined Path Length: ${robot1Path.length + robot2Path.length + robot3Path.length}")
//					}
					// Update the best combined path if it's shorter
					if (totalLength < minTotalLength) {
						minTotalLength = totalLength
						bestRobot1Path = robot1Path
						bestRobot2Path = robot2Path
						bestRobot3Path = robot3Path
					}
				}
			}
		}

		return Triple(bestRobot1Path, bestRobot2Path, bestRobot3Path)
	}

	fun bfsAllShortestPaths(
		keypad: List<List<Char>>,
		start: Pair<Int, Int>,
		target: String
	): List<String> {
		data class State(val position: Pair<Int, Int>, val path: String, val targetIndex: Int)

		val queue = ArrayDeque<State>()
		val shortestPaths = mutableListOf<String>()
		var shortestLength = Int.MAX_VALUE

		// Track visited states at each level of BFS
		val levelVisited = mutableSetOf<Pair<Pair<Int, Int>, Int>>()
		var currentLevel = mutableSetOf<Pair<Pair<Int, Int>, Int>>()

		queue.add(State(start, "", 0))
		currentLevel.add(start to 0)

		while (queue.isNotEmpty()) {
			val nextLevel = mutableSetOf<Pair<Pair<Int, Int>, Int>>()

			// Process all states at current level
			while (queue.isNotEmpty() && (queue.first().position to queue.first().targetIndex) in currentLevel) {
				val (currentPosition, currentPath, targetIndex) = queue.removeFirst()

				// If we've completed the entire target sequence, collect the path
				if (targetIndex == target.length) {
					if (currentPath.length < shortestLength) {
						shortestPaths.clear()
						shortestLength = currentPath.length
					}
					if (currentPath.length == shortestLength) {
						shortestPaths.add(currentPath)
					}
					continue
				}

				// Skip if this path is already longer than the shortest found
				if (shortestLength != Int.MAX_VALUE && currentPath.length >= shortestLength) {
					continue
				}

				val currentTarget = target[targetIndex]

				// Check all possible moves (UP, DOWN, LEFT, RIGHT, PRESS 'A')
				val directions = listOf(
					'^' to (-1 to 0),
					'v' to (1 to 0),
					'<' to (0 to -1),
					'>' to (0 to 1)
				)

				for ((move, direction) in directions) {
					val newX = currentPosition.first + direction.first
					val newY = currentPosition.second + direction.second
					val newPosition = newX to newY

					// Check if the move is valid and not visited in previous levels
					if (newX in keypad.indices &&
						newY in keypad[newX].indices &&
						keypad[newX][newY] != '#' &&
						(newPosition to targetIndex) !in levelVisited
					) {
						queue.add(State(newPosition, currentPath + move, targetIndex))
						nextLevel.add(newPosition to targetIndex)
					}
				}

				// Handle 'A' Press
				val key = keypad[currentPosition.first][currentPosition.second]
				if (key == currentTarget &&
					(currentPosition to (targetIndex + 1)) !in levelVisited
				) {
					queue.add(State(currentPosition, currentPath + "A", targetIndex + 1))
					nextLevel.add(currentPosition to (targetIndex + 1))
				}
			}

			// Update visited states and prepare for next level
			levelVisited.addAll(currentLevel)
			currentLevel = nextLevel
		}

		return shortestPaths
	}



	fun bfsAllShortestPaths_(
		keypad: List<List<Char>>,
		start: Pair<Int, Int>,
		target: String
	): List<String> {
		data class State(val position: Pair<Int, Int>, val path: String, val targetIndex: Int)

		val queue = ArrayDeque<State>()
		val visited = mutableSetOf<Pair<Pair<Int, Int>, Int>>() // Track (position, targetIndex)
		val shortestPaths = mutableListOf<String>()
		var shortestLength = Int.MAX_VALUE

		queue.add(State(start, "", 0)) // Start at the initial position and first target

		while (queue.isNotEmpty()) {
			val (currentPosition, currentPath, targetIndex) = queue.removeFirst()

			// If we've completed the entire target sequence, collect the path
			if (targetIndex == target.length) {
				if (currentPath.length < shortestLength) {
					shortestPaths.clear()
					shortestLength = currentPath.length
				}
				if (currentPath.length == shortestLength) {
					shortestPaths.add(currentPath)
				}
				continue
			}

			// Skip if this state has already been visited
			if (visited.contains(currentPosition to targetIndex)) {
				continue
			}
			visited.add(currentPosition to targetIndex)

			// Get the current target key from the sequence
			val currentTarget = target[targetIndex]

			// Check all possible moves (UP, DOWN, LEFT, RIGHT, PRESS 'A')
			val directions = listOf(
				'^' to (-1 to 0),
				'v' to (1 to 0),
				'<' to (0 to -1),
				'>' to (0 to 1)
			)
			for ((move, direction) in directions) {
				val newX = currentPosition.first + direction.first
				val newY = currentPosition.second + direction.second
				val newPosition = newX to newY

				// Check if the move is valid (within bounds and not a wall)
				if (newX in keypad.indices && newY in keypad[newX].indices && keypad[newX][newY] != '#') {
					queue.add(State(newPosition, currentPath + move, targetIndex))
				}
			}

			// Handle 'A' Press
			val key = keypad[currentPosition.first][currentPosition.second]
			if (key == currentTarget) {
				queue.add(State(currentPosition, currentPath + "A", targetIndex + 1))
			}
		}

		return shortestPaths
	}


	private fun getNumericCode(code: String): Int {
		return code.filter { it.isDigit() }.toInt()
	}

	fun validateRobotSequence(
		longSequence: String,
		numericalKeypad: List<List<Char>>,
		directionalKeypad: List<List<Char>>,
		desiredCode: String,
		isRobot1: Boolean
	): Boolean {
		val debug = false
		if (isRobot1) {
			// Validation logic for Robot 1 remains the same
			var currentPosition = findKeyPosition(numericalKeypad, 'A') // Start on 'A'
			val output = StringBuilder()

			if (debug)
				println("Starting validation for Robot 1. Initial position: $currentPosition")

			for ((index, action) in longSequence.withIndex()) {
				when (action) {
					'^' -> {
						val newPosition = currentPosition.first - 1 to currentPosition.second
						if (
							newPosition.first in numericalKeypad.indices &&
							newPosition.second in numericalKeypad[newPosition.first].indices &&
							numericalKeypad[newPosition.first][newPosition.second] != '#'
						) {
							currentPosition = newPosition
							if (debug)
								println("Step $index: Moved UP to $currentPosition")
						} else {
							if (debug)
								println("Step $index: INVALID move UP to $newPosition")
							return false
						}
					}

					'v' -> {
						val newPosition = currentPosition.first + 1 to currentPosition.second
						if (
							newPosition.first in numericalKeypad.indices &&
							newPosition.second in numericalKeypad[newPosition.first].indices &&
							numericalKeypad[newPosition.first][newPosition.second] != '#'
						) {
							currentPosition = newPosition
							if (debug)
								println("Step $index: Moved DOWN to $currentPosition")
						} else {
							if (debug)
								println("Step $index: INVALID move DOWN to $newPosition")
							return false
						}
					}

					'<' -> {
						val newPosition = currentPosition.first to currentPosition.second - 1
						if (
							newPosition.first in numericalKeypad.indices &&
							newPosition.second in numericalKeypad[newPosition.first].indices &&
							numericalKeypad[newPosition.first][newPosition.second] != '#'
						) {
							currentPosition = newPosition
							if (debug)
								println("Step $index: Moved LEFT to $currentPosition")
						} else {
							if (debug)
								println("Step $index: INVALID move LEFT to $newPosition")
							return false
						}
					}

					'>' -> {
						val newPosition = currentPosition.first to currentPosition.second + 1
						if (
							newPosition.first in numericalKeypad.indices &&
							newPosition.second in numericalKeypad[newPosition.first].indices &&
							numericalKeypad[newPosition.first][newPosition.second] != '#'
						) {
							currentPosition = newPosition
							if (debug)
								println("Step $index: Moved RIGHT to $currentPosition")
						} else {
							if (debug)
								println("Step $index: INVALID move RIGHT to $newPosition")
							return false
						}
					}

					'A' -> {
						val key = numericalKeypad[currentPosition.first][currentPosition.second]
						if (key == '#') {
							if (debug)
								println("Step $index: Error: Attempted to press an invalid key (`#`) at $currentPosition.")
							return false
						}
						output.append(key)
						if (debug)
							println("Step $index: Pressed key '$key' at $currentPosition")
					}

					else -> {
						if (debug)
							println("Step $index: Error: Unknown action '$action'")
						return false
					}
				}
			}

			val isValid = output.toString() == desiredCode
			println("Generated Output: $output (Expected: $desiredCode)")
			return isValid
		} else {
			// Validation logic for Robot 2 and beyond
			var currentPosition = findKeyPosition(directionalKeypad, 'A') // Start on 'A'
			val reconstructedPath = StringBuilder()

			if (debug)
				println("Starting validation for Robot 2+. Initial position: $currentPosition")

			for ((index, action) in longSequence.withIndex()) {
				when (action) {
					'^' -> {
						val newPosition = currentPosition.first - 1 to currentPosition.second
						if (
							newPosition.first in directionalKeypad.indices &&
							newPosition.second in directionalKeypad[newPosition.first].indices &&
							directionalKeypad[newPosition.first][newPosition.second] != '#'
						) {
							currentPosition = newPosition
							if (debug)
								println("Step $index: Moved UP to $currentPosition")
						} else {
							if (debug)
								println("Step $index: INVALID move UP to $newPosition")
							return false
						}
					}

					'v' -> {
						val newPosition = currentPosition.first + 1 to currentPosition.second
						if (
							newPosition.first in directionalKeypad.indices &&
							newPosition.second in directionalKeypad[newPosition.first].indices &&
							directionalKeypad[newPosition.first][newPosition.second] != '#'
						) {
							currentPosition = newPosition
							if (debug)
								println("Step $index: Moved DOWN to $currentPosition")
						} else {
							if (debug)
								println("Step $index: INVALID move DOWN to $newPosition")
							return false
						}
					}

					'<' -> {
						val newPosition = currentPosition.first to currentPosition.second - 1
						if (
							newPosition.first in directionalKeypad.indices &&
							newPosition.second in directionalKeypad[newPosition.first].indices &&
							directionalKeypad[newPosition.first][newPosition.second] != '#'
						) {
							currentPosition = newPosition
							if (debug)
								println("Step $index: Moved LEFT to $currentPosition")
						} else {
							if (debug)
								println("Step $index: INVALID move LEFT to $newPosition")
							return false
						}
					}

					'>' -> {
						val newPosition = currentPosition.first to currentPosition.second + 1
						if (
							newPosition.first in directionalKeypad.indices &&
							newPosition.second in directionalKeypad[newPosition.first].indices &&
							directionalKeypad[newPosition.first][newPosition.second] != '#'
						) {
							currentPosition = newPosition
							if (debug)
								println("Step $index: Moved RIGHT to $currentPosition")
						} else {
							if (debug)
								println("Step $index: INVALID move RIGHT to $newPosition")
							return false
						}
					}

					'A' -> {
						val direction = directionalKeypad[currentPosition.first][currentPosition.second]
						reconstructedPath.append(direction)
						if (debug)
							println("Step $index: Pressed '$direction' to reconstruct Robot 1's path")
					}

					else -> {
						if (debug)
							println("Step $index: Error: Unknown action '$action'")
						return false
					}
				}
			}

			val isValid = reconstructedPath.toString() == desiredCode
			println("Generated Output: $reconstructedPath (Expected: $desiredCode)")
			return isValid
		}
	}

//	fun findBestPathForNextRobot_(
//		allPaths: List<String>,
//		keypad: List<List<Char>>,
//		start: Pair<Int, Int>,
//		nextRobotTarget: String
//	): String {
//		var bestPath = ""
//		var minCost = Int.MAX_VALUE
//
//		for (path in allPaths) {
//			// Simulate the next robot's BFS to compute the cost of reproducing this path
//			val nextRobotPath = bfsPathWithTransitions(keypad, start, path)
//			val cost = nextRobotPath.length
//
//			// Choose the path with the smallest reproduction cost
//			if (cost < minCost) {
//				minCost = cost
//				bestPath = path
//			}
//		}
//
//		return bestPath
//	}
//
//	fun bfsPathWithTransitions(
//		keypad: List<List<Char>>,
//		start: Pair<Int, Int>,
//		targetSequence: String
//	): String {
//		data class State(val position: Pair<Int, Int>, val path: String, val targetIndex: Int)
//
//		val queue = ArrayDeque<State>()
//		val visited = mutableSetOf<Pair<Pair<Int, Int>, Int>>() // Track (position, targetIndex)
//		queue.add(State(start, "", 0)) // Start at the initial position and first target
//
//		while (queue.isNotEmpty()) {
//			val (currentPosition, currentPath, targetIndex) = queue.removeFirst()
//
//			// If we've completed the entire target sequence, return the path
//			if (targetIndex == targetSequence.length) {
//				return currentPath
//			}
//
//			// Skip if this state has already been visited
//			if (visited.contains(currentPosition to targetIndex)) {
//				continue
//			}
//			visited.add(currentPosition to targetIndex)
//
//			// Get the current target key from the sequence
//			val currentTarget = targetSequence[targetIndex]
//
//			// Check all possible moves (UP, DOWN, LEFT, RIGHT, PRESS 'A')
//			val directions = listOf(
//				'^' to (-1 to 0),
//				'v' to (1 to 0),
//				'<' to (0 to -1),
//				'>' to (0 to 1)
//			)
//			for ((move, direction) in directions) {
//				val newX = currentPosition.first + direction.first
//				val newY = currentPosition.second + direction.second
//				val newPosition = newX to newY
//
//				// Check if the move is valid (within bounds and not a wall)
//				if (newX in keypad.indices && newY in keypad[newX].indices && keypad[newX][newY] != '#') {
//					queue.add(State(newPosition, currentPath + move, targetIndex))
//				}
//			}
//
//			// Handle 'A' Press
//			val key = keypad[currentPosition.first][currentPosition.second]
//			if (key == currentTarget) {
//				queue.add(State(currentPosition, currentPath + "A", targetIndex + 1))
//			}
//		}
//
//		// If no path is found, return an empty string (shouldn't happen in valid cases)
//		return ""
//	}

//	fun generateOptimizedRobotPaths_(
//		targetSequence: String,
//		keypad: List<List<Char>>
//	): String {
//		var currentPosition = findKeyPosition(keypad, 'A') // Start at 'A'
//		val result = StringBuilder()
//
//		for (target in targetSequence) {
//			// Step 1: Generate all shortest paths to the target
//			val allShortestPaths = bfsAllShortestPaths(keypad, currentPosition, target)
//
//			// Step 2: Choose the best path for the next robot
//			val bestPath = findBestPathForNextRobot_(allShortestPaths, keypad, currentPosition, targetSequence)
//
//			// Append the best path and update the current position
//			result.append(bestPath)
//			currentPosition = findKeyPosition(keypad, target)
//		}
//
//		return result.toString()
//	}


//	fun bfsAllShortestPaths(
//		keypad: List<List<Char>>,
//		start: Pair<Int, Int>,
//		target: Char
//	): List<String> {
//		data class State(val position: Pair<Int, Int>, val path: String)
//
//		val queue = ArrayDeque<State>()
//		val visited = mutableSetOf<Pair<Int, Int>>()
//		val shortestPaths = mutableListOf<String>()
//		var shortestLength = Int.MAX_VALUE
//
//		queue.add(State(start, ""))
//
//		while (queue.isNotEmpty()) {
//			val (currentPosition, currentPath) = queue.removeFirst()
//			val (x, y) = currentPosition
//
//			// If we've found the target key
//			if (keypad[x][y] == target) {
//				// If this is the shortest path so far, clear previous paths and update
//				if (currentPath.length < shortestLength) {
//					shortestPaths.clear()
//					shortestLength = currentPath.length
//				}
//				// Add this path if it matches the shortest length
//				if (currentPath.length == shortestLength) {
//					shortestPaths.add(currentPath + "A") // Append 'A' for the button press
//				}
//				continue
//			}
//
//			// Skip if already visited
//			if (currentPosition in visited) continue
//			visited.add(currentPosition)
//
//			// Explore all possible moves
//			val directions = listOf(
//				'^' to (-1 to 0), // UP
//				'v' to (1 to 0),  // DOWN
//				'<' to (0 to -1), // LEFT
//				'>' to (0 to 1)   // RIGHT
//			)
//			for ((move, direction) in directions) {
//				val newX = x + direction.first
//				val newY = y + direction.second
//
//				// Check bounds and avoid walls
//				if (newX in keypad.indices && newY in keypad[newX].indices && keypad[newX][newY] != '#') {
//					queue.add(State(newX to newY, currentPath + move))
//				}
//			}
//		}
//
//		return shortestPaths
//	}

	// Helper function to find the position of a key on a keypad
	fun findKeyPosition(keypad: List<List<Char>>, key: Char): Pair<Int, Int> {
		for (i in keypad.indices) {
			for (j in keypad[i].indices) {
				if (keypad[i][j] == key) {
					return i to j
				}
			}
		}
		throw IllegalArgumentException("Key $key not found on the keypad")
	}

	override fun b(): Any {
		return 0
	}
}