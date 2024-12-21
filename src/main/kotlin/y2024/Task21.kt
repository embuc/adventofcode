package y2024

import Task

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

	override fun a(): Any {
		var sum = 0L
		val inputSequence = input

		for (targetSequence in inputSequence) {
			val paths = mapAllRobotMoves(targetSequence, 2)
			sum += paths[2].length * getNumericCode(targetSequence)
		}
		return sum
	}

	private fun getNumericCode(code: String): Int {
		return code.filter { it.isDigit() }.toInt()
	}

	// my first implementation, nice but slow for part II
	fun mapAllRobotMoves(targetSequence: String, robots: Int): List<String> {
		val paths = MutableList(robots + 1) { "" } // Paths for all robots
		val currentKeys = MutableList(robots + 1) { 'A' } // Track each robot's current key ('A' initially)

		// Robot 1 maps its moves on the numerical keypad
		for (targetKey in targetSequence) {
			val move = numericalBestMoves[currentKeys[0] to targetKey]
				?: error("No precomputed path for Robot 1 from ${currentKeys[0]} to $targetKey")
			paths[0] += move
			currentKeys[0] = targetKey
		}

		// Robots 2 to N map their moves based on the previous robot's path
		for (robot in 1..robots) {
			for (move in paths[robot - 1]) { // Use the path of the previous robot
				val step = directionalBestMoves[currentKeys[robot] to move]
					?: error("No precomputed path for Robot $robot from ${currentKeys[robot]} to $move")
				paths[robot] += step
				currentKeys[robot] = move
			}
		}

		return paths
	}

	fun findOptimalPathLength(targetSequence: String, robots: Int): Long {
		// Memoization: Key is (current transition, robot level), using robot as part of the state was crucial
		val memo = mutableMapOf<Triple<Char, Char, Int>, Long>()

		fun calculatePathLength(currentTransition: Pair<Char, Char>, robot: Int): Long {
			// Base case: Last robot processes no further levels
			if (robot == robots) return 0L

			val state = Triple(currentTransition.first, currentTransition.second, robot)
			if (state in memo) return memo[state]!!

			val bestMoveMap = if (robot == 0) numericalBestMoves else directionalBestMoves

			// Get the subPath for this transition
			val subPath = bestMoveMap[currentTransition]!!

			var subSum = 0L

			// Add the path length directly if this is the last robot
			if (robot == robots - 1) subSum += subPath.length

			// Transform the subPath into individual transitions
			var startKey = 'A'
			val transitions = mutableListOf<Pair<Char, Char>>()
			for (c in subPath) {
				transitions.add(startKey to c)
				startKey = c
			}

			for (transition in transitions) {
				subSum += calculatePathLength(transition, robot + 1)
			}

			memo[state] = subSum
			return subSum
		}

		// Transform the target sequence into transitions (starting from 'A')
		var startKey = 'A'
		val transitions = mutableListOf<Pair<Char, Char>>()
		for (c in targetSequence) {
			transitions.add(startKey to c)
			startKey = c
		}
		var totalCost = 0L
		for (transition in transitions) {
			totalCost += calculatePathLength(transition, 0)
		}

		return totalCost
	}

	override fun b(): Any {
		var sum = 0L
		val inputSequence = input

		val robots = 26 // 25 directional and one numerical
		for (targetSequence in inputSequence) {
			val paths = findOptimalPathLength(targetSequence, robots)
			sum += paths * getNumericCode(targetSequence)
		}
		return sum
	}

	val directionalBestMoves = mapOf(
		'^' to '^' to "A",
		'^' to 'A' to ">A",
		'^' to '<' to "v<A",
		'^' to 'v' to "vA",
		'^' to '>' to "v>A",

		'A' to '^' to "<A",
		'A' to 'A' to "A",
		'A' to '<' to "v<<A",
		'A' to 'v' to "<vA",
		'A' to '>' to "vA",

		'<' to '^' to ">^A",
		'<' to 'A' to ">>^A",
		'<' to '<' to "A",
		'<' to 'v' to ">A",
		'<' to '>' to ">>A",

		'v' to '^' to "^A",
		'v' to 'A' to "^>A",
		'v' to '<' to "<A",
		'v' to 'v' to "A",
		'v' to '>' to ">A",

		'>' to '^' to "<^A",
		'>' to 'A' to "^A",
		'>' to '<' to "<<A",
		'>' to 'v' to "<A",
		'>' to '>' to "A"
	)

	val numericalBestMoves = mapOf(
		'7' to '7' to "A",
		'7' to '8' to ">A",
		'7' to '9' to ">>A",
		'7' to '4' to "vA",
		'7' to '5' to "v>A",
		'7' to '6' to "v>>A",
		'7' to '1' to "vvA",
		'7' to '2' to "vv>A",
		'7' to '3' to "vv>>A",
		'7' to '0' to ">vvvA",
		'7' to 'A' to ">>vvvA",

		'8' to '7' to "<A",
		'8' to '8' to "A",
		'8' to '9' to ">A",
		'8' to '4' to "<vA",
		'8' to '5' to "vA",
		'8' to '6' to "v>A",
		'8' to '1' to "<vvA",
		'8' to '2' to "vvA",
		'8' to '3' to "vv>A",
		'8' to '0' to "vvvA",
		'8' to 'A' to "vvv>A",

		'9' to '7' to "<<A",
		'9' to '8' to "<A",
		'9' to '9' to "A",
		'9' to '4' to "<<vA",
		'9' to '5' to "<vA",
		'9' to '6' to "vA",
		'9' to '1' to "<<vvA",
		'9' to '2' to "<vvA",
		'9' to '3' to "vvA",
		'9' to '0' to "<vvvA",
		'9' to 'A' to "vvvA",

		'4' to '7' to "^A",
		'4' to '8' to "^>A",
		'4' to '9' to "^>>A",
		'4' to '4' to "A",
		'4' to '5' to ">A",
		'4' to '6' to ">>A",
		'4' to '1' to "vA",
		'4' to '2' to "v>A",
		'4' to '3' to "v>>A",
		'4' to '0' to ">vvA",
		'4' to 'A' to ">>vvA",

		'5' to '7' to "<^A",
		'5' to '8' to "^A",
		'5' to '9' to "^>A",
		'5' to '4' to "<A",
		'5' to '5' to "A",
		'5' to '6' to ">A",
		'5' to '1' to "<vA",
		'5' to '2' to "vA",
		'5' to '3' to "v>A",
		'5' to '0' to "vvA",
		'5' to 'A' to "vv>A",

		'6' to '7' to "<<^A",
		'6' to '8' to "<^A",
		'6' to '9' to "^A",
		'6' to '4' to "<<A",
		'6' to '5' to "<A",
		'6' to '6' to "A",
		'6' to '1' to "<<vA",
		'6' to '2' to "<vA",
		'6' to '3' to "vA",
		'6' to '0' to "<vvA",
		'6' to 'A' to "vvA",

		'1' to '7' to "^^A",
		'1' to '8' to "^^>A",
		'1' to '9' to "^^>>A",
		'1' to '4' to "^A",
		'1' to '5' to "^>A",
		'1' to '6' to "^>>A",
		'1' to '1' to "A",
		'1' to '2' to ">A",
		'1' to '3' to ">>A",
		'1' to '0' to ">vA",
		'1' to 'A' to ">>vA",

		'2' to '7' to "<^^A",
		'2' to '8' to "^^A",
		'2' to '9' to ">^^A",
		'2' to '4' to "<^A",
		'2' to '5' to "^A",
		'2' to '6' to ">^A",
		'2' to '1' to "<A",
		'2' to '2' to "A",
		'2' to '3' to ">A",
		'2' to '0' to "vA",
		'2' to 'A' to "v>A",

		'3' to '7' to "<<^^A",
		'3' to '8' to "<^^A",
		'3' to '9' to "^^A",
		'3' to '4' to "<<^A",
		'3' to '5' to "<^A",
		'3' to '6' to "^A",
		'3' to '1' to "<<A",
		'3' to '2' to "<A",
		'3' to '3' to "A",
		'3' to '0' to "<vA",
		'3' to 'A' to "vA",

		'0' to '7' to "^^^<A",
		'0' to '8' to "^^^A",
		'0' to '9' to "^^^>A",
		'0' to '4' to "^^<A",
		'0' to '5' to "^^A",
		'0' to '6' to "^^>A",
		'0' to '1' to "^<A",
		'0' to '2' to "^A",
		'0' to '3' to "^>A",
		'0' to '0' to "A",
		'0' to 'A' to ">A",

		'A' to '7' to "^^^<<A",
		'A' to '8' to "<^^^A",
		'A' to '9' to "^^^A",
		'A' to '4' to "^^<<A",
		'A' to '5' to "<^^A",
		'A' to '6' to "^^A",
		'A' to '1' to "^<<A",
		'A' to '2' to "<^A",
		'A' to '3' to "^A",
		'A' to '0' to "<A",
		'A' to 'A' to "A"
	)
}