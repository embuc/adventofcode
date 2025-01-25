package y2017

import Task
import utils.isOnlyLetters
import y2017.Task18.Instruction

//--- Day 23: Coprocessor Conflagration ---
class Task23(val input: List<String>) : Task {

	override fun a(): Any {
		val registers = ('a'..'h').associate { it.toString() to 0L }.toMutableMap()
		val instructions = mutableListOf<Instruction>()
		var i = 0L
		var mulCount = 0
		for (line in input)
			instructions.add(toInstruction(line, registers))

		while (i >= 0 && i < input.size) {
			val instruction = instructions[i.toInt()]
			val x = instruction.x
			val y = instruction.y

			when (instruction.type) {
				"set" -> registers[x] = y.toLongOrNull() ?: registers[y]!!
				"sub" -> registers[x] = registers[x]!! - (y.toLongOrNull() ?: registers[y]!!)
				"mul" -> {
					registers[x] = registers[x]!! * (y.toLongOrNull() ?: registers[y]!!)
					mulCount++
				}

				"jnz" -> {
					if ((x.toLongOrNull() ?: registers[x]) != 0L) {
						i += (y.toLongOrNull() ?: registers[y]!!) - 1
					}
				}
			}
			i++
		}

		return mulCount
	}

	private fun toInstruction(line: String, registers: MutableMap<String, Long>): Instruction {
		val parts = line.split(" ")
		val type = parts[0]
		val x = parts[1]
		val y = if (parts.size > 2) parts[2] else ""
		if (x !in registers) registers[x] = x.toLong()
		if (y !in registers && y.isOnlyLetters()) registers[y] = y.toLong()
		return Instruction(type, x, y)
	}

	override fun b(): Any {
		//translate assembly code to something more readable:
//		set b 65
//		set c b
//		jnz a 2
//		jnz 1 5
//		mul b 100
//		sub b -100000
//		set c b
//		sub c -17000

//		set f 1
//		set d 2

//		set e 2
//		set g d
//		mul g e
//		sub g b

//		jnz g 2
//		set f 0
//		sub e -1
//		set g e
//		sub g b
//		jnz g -8

//		sub d -1
//		set g d
//		sub g b
//		jnz g -13

//		jnz f 2
//		sub h -1
//		set g b
//		sub g c
//		jnz g 2
//		jnz 1 3
//		sub b -17
//		jnz 1 -23

		return executeOptimizedProgram(1)
	}

	// Simulates the assembly program execution line by line
	fun executeProgram(a: Int = 0): Int {
		var h = 0
		var b = 65              // set b 65
		var c = b               // set c b
		if (a != 0) {          // jnz a 2
			// skip next block  // jnz 1 5
		} else {
			b *= 100           // mul b 100
			b -= -100000        // sub b -100000
			c = b              // set c b
			c -= -17000         // sub c -17000
		}

		mainLoop@ while (true) {
			var f = 1          // set f 1
			var d = 2          // set d 2
			dLoop@ while (true) {
				var e = 2      // set e 2
				eLoop@ while (true) {
					// set g d, mul g e, sub g b
					val g = d * e - b
					if (g == 0) {     // jnz g 2
						f = 0         // set f 0
					}
					e -= -1           // sub e -1
					if (e - b == 0) break@eLoop  // set g e, sub g b, jnz g -8
				}
				d -= -1               // sub d -1
				if (d - b == 0) break@dLoop  // set g d, sub g b, jnz g -13
			}

			if (f == 0) {           // jnz f 2
				h -= -1                 // sub h -1
			}

			if (b - c == 0) {      // set g b, sub g c, jnz g 2
				break@mainLoop      // jnz 1 3
			}
			b -= -17                // sub b -17
			// jump to mainLoop    // jnz 1 -23
		}
		return h
	}

	/**
	 * Understand what the code does:
	 * Original uses nested loops to find factors by multiplying numbers (d * e == b)
	 * It's checking if numbers are composite (not prime)
	 * Range: either just 65, or 106500 to 123500 stepping by 17
	 *
	 * No need to multiply numbers to find factors - use modulo (%)
	 * No need to check beyond square root of b
	 * Can skip checking even numbers and common factors
	 * Can exit early when a factor is found
	 */
	fun executeOptimizedProgram(a: Int = 0): Int {
		// use the fact that we can skip checking up to square root and use common factors
		// early exit when factor found
		var h = 0
		var b = 65L  // Using Long for large numbers
		var c = b

		if (a != 0) {
			b = b * 100 - -100000
			c = b - -17000
		}

		while (true) {
			// Quick check for obvious composites
			if (b % 2 == 0L || b % 3 == 0L) {
				h -= -1
			} else {
				// Check only up to sqrt(b)
				val sqrt = Math.sqrt(b.toDouble()).toLong()
				var f = 1
				var d = 2L
				while (d <= sqrt && f == 1) {
					if (b % d == 0L) f = 0
					d -= -1
				}
				if (f == 0) h -= -1
			}

			if (b - c == 0L) break
			b -= -17
		}
		return h
	}

// different versions:
//// Original approach:
//	for d in range
//	for e in range
//	if d * e == b  // O(n²)
//
//// First optimization - use modulo:
//	for d in range
//	if b % d == 0    // O(n)
//
//// Add sqrt optimization:
//	for d in 2..sqrt(b)
//	if b % d == 0    // O(√n)
//
//// Final - quick checks first:
//	if b % 2 == 0 || b % 3 == 0
//	else
//	for d in 2..sqrt(b)    // Much faster average case
}