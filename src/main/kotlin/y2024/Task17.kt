package y2024

import Task
import kotlin.math.pow

/*--- Day 17: Chronospatial Computer ---*/
class Task17(val input: List<String>) : Task {

	override fun a(): Any {
		val regA = input[0].split(": ")[1].toLong()
		val regB = input[1].split(": ")[1].toLong()
		val regC = input[2].split(": ")[1].toLong()
		val program = input[4].replace("Program:", "").split(",").map { it.trim().toLong() }
		return runOnce(program, regA, regB, regC)
	}

	private fun runOnce(program: List<Long>, regAA: Long, regBB: Long, regCC: Long): String {
		var regA = regAA
		var regB = regBB
		var regC = regCC
		val output = mutableListOf<Long>()
		var index = 0
		while (index < program.size) {
			val i = program[index]
			val ii = program[index + 1].toLong()
			when (i) {
				0L -> {
					regA = adv(ii, regA, regB, regC).toLong()
				}
				1L -> {
					regB = bxl(ii, regA, regB, regC)
				}
				2L -> {
					regB = bst(ii, regA, regB, regC)
				}
				3L -> {
					if(regA != 0L) {
						index = ii.toInt()
						continue
					}
				}
				4L -> {
					regB = bxc(ii, regA, regB, regC)
				}
				5L -> {
					output.add(out(ii, regA, regB, regC))
				}
				6L -> {
					regB = bdv(ii, regA, regB, regC)
				}
				7L -> {
					regC = cdv(ii, regA, regB, regC)
				}
			}
			index += 2
		}
		return output.joinToString(",")
	}

	private fun adv(operand: Long, regA: Long, regB:Long, regC: Long): Long {
		val op = expandOp(operand, regA, regB, regC)
		val denominator = 2.0.pow(op.toDouble()).toLong()
		val result = regA / denominator
		return result
	}
	private fun bxl(i: Long, regA: Long, regB:Long, regC: Long): Long {
		return regB xor i
	}
	private fun bst(i: Long, regA: Long, regB:Long, regC: Long): Long {
		return expandOp(i,regA, regB, regC) % 8
	}
	private fun bxc(i: Long, regA: Long, regB:Long, regC: Long): Long {
		return regB xor regC
	}
	private fun out(i: Long, regA: Long, regB:Long, regC: Long): Long {
		return expandOp(i,regA, regB, regC) % 8
	}
	private fun bdv(i: Long, regA: Long, regB:Long, regC: Long): Long {
		return adv(i,regA, regB, regC)
	}
	private fun cdv(i: Long, regA: Long, regB:Long, regC: Long): Long {
		return adv(i,regA, regB, regC)
	}
	private fun expandOp(operand: Long, regA: Long, regB:Long, regC: Long): Long {
		return when (operand) {
			4L -> regA
			5L -> regB
			6L -> regC
			else -> operand
		}
	}

	override fun b(): Any {
		val program = input[4].replace("Program:", "").split(",").map { it.trim().toLong() }
		return dfs_backtrack(program.map { it.toLong() }, 0L, 0)
	}

	fun dfs_backtrack(program: List<Long>, cur: Long = 0L, pos: Int = 0): Long {
		// 1. Base Case: If we have reached the end of the program (all positions filled)
		if (pos == program.size) {
//			println("Success: $cur")
			return cur // The current number `cur` is one of valid solutions
		}

		var minValidResult = Long.MAX_VALUE

		// 2. Candidate Generation: Loop through all possible candidates (0 to 7)
		for (i in 0..7) {
			// Generate the next number by appending `i` (shift left and add `i`)
			val nextNum = (cur shl 3) + i
//			println("program: $program")
//			println("pos: $pos")
//			println("cur: $cur")
//			println("nextNum: $nextNum")

			// Simulate running the program with the candidate number (`nextNum`)
			val execResult = runOnce(program, nextNum, 0L, 0L).split(",").map { it.toLong() }
//			println("execResult: $execResult")
//			println("program.subList(program.size - pos - 1, program.size): ${program.subList(program.size - pos - 1, program.size)}")

			// 3. Pruning: Check if the result matches the expected sublist of the program
			if (execResult == program.subList(program.size - pos - 1, program.size)) {
				// If valid, recursively explore further with the updated number and position
				val result = dfs_backtrack(program, nextNum, pos + 1)
//				Once the recursion completes for one branch, the algorithm "backtracks" by simply continuing the loop to try the next candidate.
				// 4. Backtracking: Update the minimum successful result from recursive calls
				minValidResult = minOf(minValidResult, result)
			}
		}

		// Return the smallest valid solution found at this level of recursion
		return minValidResult
	}
}
