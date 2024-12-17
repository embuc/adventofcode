package y2024

import Task
import kotlin.math.pow

/*--- Day 17: Chronospatial Computer ---*/
class Task17(val input: List<String>) : Task {

	override fun a(): Any {
		// parse input
//		Register A: 729
//		Register B: 0
//		Register C: 0
//
//		Program: 0,1,5,4,3,0
		var regA = input[0].split(": ")[1].toLong()
		var regB = input[1].split(": ")[1].toLong()
		var regC = input[2].split(": ")[1].toLong()
		val program = input[4].replace("Program:", "").split(",").map { it.trim().toInt() }
//		println(regA)
//		println(regB)
//		println(regC)
//		println(program)
		val output = mutableListOf<Long>()
//		for (j in program.indices step 2) {
		var index = 0

		while (index < program.size) {
			val i = program[index]
			val ii = program[index + 1].toLong()
			when (i) {
				0 -> {
					regA = adv(ii, regA, regB, regC).toInt().toLong()
				}
				1 -> {
					regB = bxl(ii, regA, regB, regC)
				}
				2 -> {
					regB = bst(ii, regA, regB, regC)
				}
				3 -> {
					if(regA != 0L) {
						val jump = jnz(ii, regA, regB, regC).toInt()
						index = jump
						continue
					}
				}
				4 -> {
					regB = bxc(ii, regA, regB, regC)
				}
				5 -> {
					output.add(out(ii, regA, regB, regC))
				}
				6 -> {
					regB = bdv(ii, regA, regB, regC).toInt().toLong()
				}
				7 -> {
					regC = cdv(ii, regA, regB, regC).toInt().toLong()
				}
				else -> 0
			}
//			output.add(res)
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
	private fun jnz(i: Long, regA: Long, regB:Long, regC: Long): Long {
		return i
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
		return 0
	}

}
