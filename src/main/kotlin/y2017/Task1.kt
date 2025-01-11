package y2017

import Task

//--- Day 1: Inverse Captcha ---
class Task1(val input:String) : Task {

	override fun a(): Any {
		var sameDigit:Char = ' '
		var sum = 0
		for (i in 0..(input.length)) {
			val ix  = i % input.length
			val digit = input[ix % input.length]
			if(digit == sameDigit) {
				sum += digit.code - '0'.code
			}
			sameDigit = digit
		}
		println()
		return sum
	}

	override fun b(): Any {
		var sum = 0
		for (i in 0..(input.length - 1)) {
			val ix  = i % input.length
			val ix2  = (i + input.length/2) % input.length
			val digit = input[ix % input.length]
			val otherDigit = input[ix2]
			if(digit == otherDigit) {
				sum += digit.code - '0'.code
			}
		}
		println()
		return sum
	}
}
