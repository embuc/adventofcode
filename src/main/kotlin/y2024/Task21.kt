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

	override fun a(): Any {
		//strategy is to find the shortest path from the start to the end for me, then find the shortest path from the
		//start to the end for each robot, then calculate the complexity
		val code = "029A"
//		val code = "379A"

		var sum = 0
//		for (code in input) {
		val numericalKeypad = listOf(
			listOf('7', '8', '9'),
			listOf('4', '5', '6'),
			listOf('1', '2', '3'),
			listOf('#', '0', 'A')
		)

		// Step 1: Generate Robot 1's path to type the numeric code
		val robot1Path = generateRobot1Path(code)
		println("Robot 1's path: $robot1Path")
		println("length of robot 1 path: ${robot1Path.length}")
		val isRobot1Valid = validateRobotSequence(robot1Path, numericalKeypad, directionalKeypad, code, isRobot1 = true)
		println("Robot 1 Validation Result: $isRobot1Valid")

		val robot2Path = generateRobotPath(directionalKeypad, robot1Path)
		// Step 2: Generate Robot 2's path to control Robot 1
//			val robot2Path = generateRobotPath(directionalKeypad, robot1Path)

			println("Robot 2's path: $robot2Path")
			println("length of robot 2 path: ${robot2Path.length}")
		val isRobot2Valid = validateRobotSequence(robot2Path, numericalKeypad, directionalKeypad, robot1Path, isRobot1 = false)
		println("Robot 2 Validation Result: $isRobot2Valid")

		val robot3Path = generateRobotPath(directionalKeypad, robot2Path)
		// Step 3: Generate Robot 3's path to control Robot 2
//			val robot3Path = generateRobotPath(directionalKeypad, robot2Path)
//
			println("Robot 3's path: $robot3Path")
		println("length of robot 3 path: ${robot3Path.length}")
		sum += robot3Path.length * getNumericCode(code)

//		val isValid = validateRobotSequence(robot3Path, numericKeypad, code)
		val isRobot3Valid = validateRobotSequence(robot3Path, numericalKeypad, directionalKeypad, robot2Path, isRobot1 = false)
		println("Robot 3 Validation Result: $isRobot3Valid")


//		}


		// Step 4: Generate Robot 4's path to control Robot 3
//		val robot4Path = generateRobotPath(directionalKeypad, robot3Path)
//		println("Robot 4's path: $robot4Path")
//		println("length of robot 4 path: ${robot4Path.length}")
		return sum
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

	// Directions common to all keypads
	val directions = listOf(
		Pair(-1, 0), // ^ (up)
		Pair(1, 0),  // v (down)
		Pair(0, -1), // < (left)
		Pair(0, 1)   // > (right)
	)
	val directionKeys = listOf('^', 'v', '<', '>')

	// Generalized BFS function (ensures shortest path is found)
	fun bfsPath(keypad: List<List<Char>>, start: Pair<Int, Int>, target: Char): String {
		val queue = LinkedList<Triple<Pair<Int, Int>, String, Set<Pair<Int, Int>>>>()
		queue.add(Triple(start, "", setOf(start))) // (current position, path so far, visited positions)

		while (queue.isNotEmpty()) {
			val (current, path, visited) = queue.poll()
			val (x, y) = current

			// If we've found the target key
			if (keypad[x][y] == target) {
				return path + "A" // Append 'A' to indicate a button press
			}

			// Explore all possible moves
			for ((i, direction) in directions.withIndex()) {
				val newX = x + direction.first
				val newY = y + direction.second


				// Check bounds
				if (newX in keypad.indices && newY in keypad[newX].indices) {
					// Skip walls but allow exploration around them
					if (keypad[newX][newY] == '#') continue

					// Avoid revisiting positions
					if ((newX to newY) !in visited) {
						queue.add(Triple(newX to newY, path + directionKeys[i], visited + (newX to newY)))
					}
				}

			}
		}

		throw IllegalArgumentException("Target $target cannot be reached from $start")
	}

	// Function to generate the path for Robot 1 to type a numeric code
	fun generateRobot1Path(code: String): String {
		var currentPosition = findKeyPosition(numericKeypad, 'A') // Start at 'A'
		val result = StringBuilder()

		for (digit in code) {
			val pathToDigit = bfsPath(numericKeypad, currentPosition, digit)
			result.append(pathToDigit)
			// Update current position after moving to the digit
			currentPosition = findKeyPosition(numericKeypad, digit)
		}

		return result.toString()
	}

	// Function to generate the path for any robot based on a previous robot's path
	fun generateRobotPath(currentKeypad: List<List<Char>>, previousRobotPath: String): String {
		var currentPosition = findKeyPosition(currentKeypad, 'A') // Start at 'A'
		val result = StringBuilder()

		for (action in previousRobotPath) {
			if (action in directionKeys || action == 'A') {
				// Find the shortest path to the action
				val pathToAction = bfsPath(currentKeypad, currentPosition, action)
				result.append(pathToAction)
				// Update current position after the action
				currentPosition = findKeyPosition(currentKeypad, action)
			}
		}

		return result.toString()
	}

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