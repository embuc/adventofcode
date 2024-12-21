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
//	One directional keypad that you are using.
//	Two directional keypads that robots are using.
//	One numeric keypad (on a door) that a robot is using.
//	<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A
//	v<<A>>^A<A>AvA<^AA>A<vAAA>^A
//	<A^A>^^AvvvA
//	029A
	//	029A: <vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A
//	980A: <v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A
//	179A: <v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A
//	456A: <v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A
//	379A: <v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A

	//		The complexity of a single code (like 029A) is equal to the result of multiplying these two values:
//
//		The length of the shortest sequence of button presses you need to type on your directional keypad in order to cause the code to be typed on the numeric keypad; for 029A, this would be 68.
//		The numeric part of the code (ignoring leading zeroes); for 029A, this would be 29.
//		In the above example, complexity of the five codes can be found by calculating 68 * 29, 60 * 980, 68 * 179, 64 * 456, and 64 * 379. Adding these together produces 126384.

	override fun a(): Any {
		//strategy is to find the shortest path from the start to the end for me, then find the shortest path from the
		//start to the end for each robot, then calculate the complexity
		val codes = input.map { it.split("\n") }
		codes.forEach {
			println(it)
		}
		// Example usage
		val code = "029A"

		// Step 1: Generate Robot 1's path to type the numeric code
		val robot1Path = generateRobot1Path(code)
		println("Robot 1's path: $robot1Path")
		println("length of robot 1 path: ${robot1Path.length}")

		// Step 2: Generate Robot 2's path to control Robot 1
		val robot2Path = generateRobotPath(directionalKeypad, robot1Path)
		println("Robot 2's path: $robot2Path")
		println("length of robot 2 path: ${robot2Path.length}")

		// Step 3: Generate Robot 3's path to control Robot 2
		val robot3Path = generateRobotPath(directionalKeypad, robot2Path)
		println("Robot 3's path: $robot3Path")
		println("length of robot 3 path: ${robot3Path.length}")

		// Step 4: Generate Robot 4's path to control Robot 3
		val robot4Path = generateRobotPath(directionalKeypad, robot3Path)
		println("Robot 4's path: $robot4Path")
		println("length of robot 4 path: ${robot4Path.length}")
		return 0
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

				// Check bounds and avoid revisiting
				if (newX in keypad.indices && newY in keypad[newX].indices && (newX to newY) !in visited) {
					queue.add(Triple(newX to newY, path + directionKeys[i], visited + (newX to newY)))
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