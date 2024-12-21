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
		//the strategy is to find the shortest path from the start to the end for me, then find the shortest path from the
		//start to the end for each robot, then calculate the complexity
		var sum = 0L

//		val targetSequence = "029A"
//			279A
//			341A
//			459A
//			540A
//			085A
//		val targetSequence = "279A"
//		val targetSequence = "379A"

//		Processing sequence: 279A, Result of dfs: 72, Multiplied: 20088
//		Processing sequence: 341A, Result of dfs: 72, Multiplied: 24552
//		Processing sequence: 459A, Result of dfs: 74, Multiplied: 33966
//		Processing sequence: 540A, Result of dfs: 72, Multiplied: 38880
//		Processing sequence: 085A, Result of dfs: 66, Multiplied: 5610
//		Part 1: 123096
//		val inputSequence = listOf(
//			"341A"
//			,
//			"459A"
//			,
//			"540A"
//			,
//			"085A"
//		)
		val inputSequence = input

		for (targetSequence in inputSequence) {
			// Map all robot moves
			val (robot1Path, robot2Path, robot3Path) = mapAllRobotMoves(targetSequence)

			println("sequence: $targetSequence")
			println("Robot 1 Path: $robot1Path")
			println("path1 length: ${robot1Path.length}")
			println("Robot 2 Path: $robot2Path")
			println("path2 length: ${robot2Path.length}")
			println("Robot 3 Path: $robot3Path")
			println("path3 length: ${robot3Path.length}")
			println("multiplied: ${robot3Path.length * getNumericCode(targetSequence)}")
			sum += robot3Path.length * getNumericCode(targetSequence)
		}
		return sum
	}

	private fun getNumericCode(code: String): Int {
		return code.filter { it.isDigit() }.toInt()
	}

	fun mapAllRobotMoves(
		targetSequence: String
	): Triple<String, String, String> {
		var robot1Path = ""
		var robot2Path = ""
		var robot3Path = ""

		// Robot 1 starts at 'A' on the numerical keypad
		var robot1CurrentKey = 'A'

		// Map Robot 1's moves on the numerical keypad
		for (targetKey in targetSequence) {
			val move = numericalBestMoves[robot1CurrentKey to targetKey]
				?: error("No precomputed path for Robot 1 from $robot1CurrentKey to $targetKey")
			robot1Path += move
			robot1CurrentKey = targetKey // Update the current key
		}

		// Robot 2 starts at 'A' on the directional keypad
		var robot2CurrentKey = 'A'

		// Map Robot 2's moves on the directional keypad (based on Robot 1's path)
		for (move in robot1Path) {
			val step = directionalBestMoves[robot2CurrentKey to move]
				?: error("No precomputed path for Robot 2 from $robot2CurrentKey to $move")
			robot2Path += step
			robot2CurrentKey = move // Update the current key
		}

		// Robot 3 starts at 'A' on the directional keypad
		var robot3CurrentKey = 'A'

		// Map Robot 3's moves on the directional keypad (based on Robot 2's path)
		for (move in robot2Path) {
			val step = directionalBestMoves[robot3CurrentKey to move]
				?: error("No precomputed path for Robot 3 from $robot3CurrentKey to $move")
			robot3Path += step
			robot3CurrentKey = move // Update the current key
		}

		return Triple(robot1Path, robot2Path, robot3Path)
	}

	override fun b(): Any {
		var sum = 0L

//		val targetSequence = "029A"
//			279A
//			341A
//			459A
//			540A
//			085A
//		val targetSequence = "279A"
//		val targetSequence = "379A"

//		Processing sequence: 279A, Result of dfs: 72, Multiplied: 20088
//		Processing sequence: 341A, Result of dfs: 72, Multiplied: 24552
//		Processing sequence: 459A, Result of dfs: 74, Multiplied: 33966
//		Processing sequence: 540A, Result of dfs: 72, Multiplied: 38880
//		Processing sequence: 085A, Result of dfs: 66, Multiplied: 5610
//		Part 1: 123096
//		val inputSequence = listOf(
//			"341A"
//			,
//			"459A"
//			,
//			"540A"
//			,
//			"085A"
//		)
		val inputSequence = input

		for (targetSequence in inputSequence) {
			// Map all robot moves
			val (robot1Path, robot2Path, robot3Path) = mapAllRobotMoves(targetSequence)

			println("sequence: $targetSequence")
			println("Robot 1 Path: $robot1Path")
			println("path1 length: ${robot1Path.length}")
			println("Robot 2 Path: $robot2Path")
			println("path2 length: ${robot2Path.length}")
			println("Robot 3 Path: $robot3Path")
			println("path3 length: ${robot3Path.length}")
			println("multiplied: ${robot3Path.length * getNumericCode(targetSequence)}")
			sum += robot3Path.length * getNumericCode(targetSequence)
		}
		return sum
	}


//  	+---+---+
//  	| ^ | A |
//	+---+---+---+
//	| < | v | > |
//	+---+---+---+

//	Least turns (this becomes important when escaping the missing cell in both numeric and directional pads).
//	moving > over ^ over v over <.

	val directionalBestMoves = mapOf(
		// From '^'
		'^' to '^' to "A",
		'^' to 'A' to ">A",
		'^' to '<' to "v<A",
		'^' to 'v' to "vA",
		'^' to '>' to ">vA",

		// From 'A'
		'A' to '^' to "<A",
		'A' to 'A' to "A",
		'A' to '<' to "v<<A",
		'A' to 'v' to "v<A",
		'A' to '>' to "vA",

		// From '<'
		'<' to '^' to ">^A",
		'<' to 'A' to ">>^A",
		'<' to '<' to "A",
		'<' to 'v' to ">A",
		'<' to '>' to ">>A",

		// From 'v'
		'v' to '^' to "^A",
		'v' to 'A' to ">^A",
		'v' to '<' to "<A",
		'v' to 'v' to "A",
		'v' to '>' to ">A",

		// From '>'
		'>' to '^' to "^<A",
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