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

			println("sequence: $targetSequence")
			println("Robot 1 Path: ${paths[0]}")
			println("path1 length: ${paths[0].length}")
			println("Robot 2 Path: ${paths[1]}")
			println("path2 length: ${paths[1].length}")
			println("Robot 3 Path: ${paths[2]}")
			println("path3 length: ${paths[2].length}")
			println("multiplied: ${paths[2].length * getNumericCode(targetSequence)}")
			sum += paths[2].length * getNumericCode(targetSequence)
		}
		return sum
	}

	private fun getNumericCode(code: String): Int {
		return code.filter { it.isDigit() }.toInt()
	}

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

	fun calculateTotalPathLength(targetSequence: String, robots:Int): Long {
		val memo = mutableMapOf<Triple<Pair<Char, Char>, Int, Int>, Long>()

		fun calculatePathLength(transition: Pair<Char, Char>, robot: Int, position: Int): Long {
			// Base cases
			if (position >= targetSequence.length) return 0
			if (robot >= robots) return 0

			// Create memo key that includes current transition, robot, and position
			val memoKey = Triple(transition, robot, position)
			if (memoKey in memo) return memo[memoKey]!!

			// Get appropriate move map based on robot type
			val bestMoveMap = if (robot == 0) numericalBestMoves else directionalBestMoves

			// Get path for this transition
			val subPath = bestMoveMap[transition]!!
			var subSum = subPath.length.toLong() // Add current path length

			// Calculate next character to transition from
			val nextStartChar = transition.second

			// If there are more characters in the sequence, calculate next transitions
			if (position + 1 < targetSequence.length) {
				val nextTransition = Pair(nextStartChar, targetSequence[position + 1])
				// Recursive call for next position with same robot
				subSum += calculatePathLength(nextTransition, robot, position + 1)
			}

			// Calculate path for next robot at current position
			if (robot + 1 < robots) {
				val nextRobotTransition = Pair('A', targetSequence[position])
				subSum += calculatePathLength(nextRobotTransition, robot + 1, position)
			}

			memo[memoKey] = subSum
			return subSum
		}

		// Start the calculation with the first transition
		if (targetSequence.isEmpty()) return 0
		val initialTransition = Pair('A', targetSequence[0])
		return calculatePathLength(initialTransition, 0, 0)
	}

	fun findOptimalPathLength_org(targetSequence: String, robots: Int): Long {
		val memo = mutableMapOf<Pair<Char, Char>, Long>()
		var totalCost = 0L

		fun calculatePathLength(currentKey: Pair<Char, Char>, robot: Int): Long {
			if (robot==robots) return 0 // Base case: Last robot

			if (currentKey in memo) return memo[currentKey]!!

			//we continue in similar fashion as before we entered recursion
			val bestMoveMap = if (robot == 0) numericalBestMoves else directionalBestMoves
			//get path for this transition
			val subPath = bestMoveMap[currentKey]!!

			var subSum = 0L
			//add only if last level
			if (robot == robots - 1) subSum += subPath.length
			//transform path to transitions, first one always from A
			var x = 'A'
			val list = mutableListOf<Pair<Char, Char>>()
			for (c in subPath){
				list.add(Pair(x, c))
				x = c
			}

			for(m in list){
				subSum += calculatePathLength(m, robot + 1)
			}
			return subSum
		}

		//transform path to transitions, first one always from A
		var x = 'A'
		val list = mutableListOf<Pair<Char, Char>>()
		for (c in targetSequence){
			list.add(Pair(x, c))
			x = c
		}

		for(m in list){
			totalCost += calculatePathLength(m, 0)
		}

		return totalCost
	}


	fun findOptimalPathLength(targetSequence: String, robots: Int): Long {
		// Memoization: Key is (current transition, robot level)
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

		// Calculate the total cost starting with Robot 1 (robot = 0)
		var totalCost = 0L
		for (transition in transitions) {
			totalCost += calculatePathLength(transition, 0)
		}

		return totalCost
	}

	override fun b(): Any {
		var sum = 0L

//		val inputSequence = listOf("029A")
		val inputSequence = input

		val robots = 26
		for (targetSequence in inputSequence) {
			val paths = findOptimalPathLength(targetSequence, robots)

//			println("sequence: $targetSequence")
//			println("Robots Path length: $paths")
			sum += paths * getNumericCode(targetSequence)
		}
		return sum
	}


//  	+---+---+
//  	| ^ | A |
//	+---+---+---+
//	| < | v | > |
//	+---+---+---+

//	Least turns (this becomes important when escaping the missing cell in both numeric and directional pads).
//	moving < over v over ^ over >.

	val directionalBestMoves = mapOf(
		// From '^'
		'^' to '^' to "A",
		'^' to 'A' to ">A",
		'^' to '<' to "v<A",
		'^' to 'v' to "vA",
		'^' to '>' to "v>A",

		// From 'A'
		'A' to '^' to "<A",
		'A' to 'A' to "A",
		'A' to '<' to "v<<A",
		'A' to 'v' to "<vA",
		'A' to '>' to "vA",

		// From '<'
		'<' to '^' to ">^A",
		'<' to 'A' to ">>^A",
		'<' to '<' to "A",
		'<' to 'v' to ">A",
		'<' to '>' to ">>A",

		// From 'v'
		'v' to '^' to "^A",
		'v' to 'A' to "^>A",
		'v' to '<' to "<A",
		'v' to 'v' to "A",
		'v' to '>' to ">A",

		// From '>'
		'>' to '^' to "<^A",
		'>' to 'A' to "^A",
		'>' to '<' to "<<A",
		'>' to 'v' to "<A",
		'>' to '>' to "A"
	)

	//	moving < over ^ over v over >.
//	+---+---+---+
//	| 7 | 8 | 9 |
//	+---+---+---+
//	| 4 | 5 | 6 |
//	+---+---+---+
//	| 1 | 2 | 3 |
//	+---+---+---+
//      | 0 | A |
//      +---+---+

	val numericalBestMoves = mapOf(
		// From '7'
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

		// From '8'
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

		// From '9'
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

		// From '4'
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

		// From '5'
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

		// From '6'
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

		// From '1'
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

		// From '2'
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

		// From '3'
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

		// From '0'
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

		// From 'A'
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