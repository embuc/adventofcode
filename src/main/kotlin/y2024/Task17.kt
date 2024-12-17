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
		var regA = input[0].split(": ")[1].toInt()
		var regB = input[1].split(": ")[1].toInt()
		var regC = input[2].split(": ")[1].toInt()
		val program = input[4].replace("Program:", "").split(",").map { it.trim().toInt() }
//		println(regA)
//		println(regB)
//		println(regC)
//		println(program)
		val output = mutableListOf<Int>()
//		for (j in program.indices step 2) {
		var index = 0

		while (index < program.size) {
			val i = program[index]
			val ii = program[index + 1]
			when (i) {
				0 -> {
					regA = adv(ii, regA, regB, regC).toInt()
				}
				1 -> {
					regB = bxl(ii, regA, regB, regC)
				}
				2 -> {
					regB = bst(ii, regA, regB, regC)
				}
				3 -> {
					if(regA != 0) {
						val jump = jnz(ii, regA, regB, regC)
						index = jump
						continue
					}
				}

				4 -> {
					regC = bxc(ii, regA, regB, regC)
				}
				5 -> {
					output.add(out(ii, regA, regB, regC))
				}
				6 -> {
					regB = bdv(ii, regA, regB, regC).toInt()
				}
				7 -> {
					regC = cdv(ii, regA, regB, regC).toInt()
				}
				else -> 0
			}
//			output.add(res)
			index += 2
		}
		return output.joinToString(",")
	}

	private fun cdv(i: Int, regA: Int, regB: Int, regC: Int): Int {
		// The cdv instruction (opcode 7) works exactly like the adv instruction
		// except that the result is stored in the C register. (The numerator is still read from the A register.)
		return adv(i,regA, regB, regC)
	}

	private fun bdv(i: Int, regA: Int, regB: Int, regC: Int): Int {
		//	The bdv instruction (opcode 6) works exactly like the adv instruction except that the result
		//	is stored in the B register. (The numerator is still read from the A register.)
		return adv(i,regA, regB, regC)
	}

	private fun out(i: Int, regA: Int, regB: Int, regC: Int): Int {
		//	The out instruction (opcode 5) calculates the value of its combo operand modulo 8, then outputs that value.
		//	(If a program outputs multiple values, they are separated by commas.)
		return expandOp(i,regA, regB, regC) % 8
	}

	private fun bxc(i: Int, regA: Int, regB: Int, regC: Int): Int {
		// The bxc instruction (opcode 4) calculates the bitwise XOR of register B and register C, then stores the result
		// in register B. (For legacy reasons, this instruction reads an operand but ignores it.)
		return regB xor regC
	}

	private fun jnz(i: Int, regA: Int, regB: Int, regC: Int): Int {
		//The jnz instruction (opcode 3) does nothing if the A register is 0. However, if the A register is not zero, it
		//jumps by setting the instruction pointer to the value of its literal operand; if this instruction jumps, the
		//instruction pointer is not increased by 2 after this instruction.
		return i
	}

	private fun bst(i: Int, regA: Int, regB: Int, regC: Int): Int {
		//	The bst instruction (opcode 2) calculates the value of its combo operand modulo 8
		//	(thereby keeping only its lowest 3 bits), then writes that value to the B register.
		return expandOp(i,regA, regB, regC) % 8
	}

	private fun bxl(i: Int, regA: Int, regB: Int, regC: Int): Int {
		// The bxl instruction (opcode 1) calculates the bitwise XOR of register B and the
		// instruction's literal operand, then stores the result in register B.
		return regB xor i
	}

	private fun adv(operand: Int, a: Int, b: Int, c: Int): Int {
		//	The adv instruction (opcode 0) performs division. The numerator is the value in the A register. The denominator is
//found by raising 2 to the power of the instruction's combo operand. (So, an operand of 2 would divide A by 4 (2^2); an
//operand of 5 would divide A by 2^B.) The result of the division operation is truncated to an integer and then written
//to the A register.
		val op = expandOp(operand, a, b, c)
		val denominator = 2.0.pow(op.toDouble()).toInt()
		val result = a / denominator
		return result
	}

	private fun expandOp(operand: Int, a: Int, b: Int, c: Int): Int {
		return when (operand) {
			4 -> a
			5 -> b
			6 -> c
			else -> operand
		}
	}

	override fun b(): Any {
		return 0
	}

}
