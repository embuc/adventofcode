package y2016

import Task

//--- Day 21: Scrambled Letters and Hash ---
class Task21(val input: List<String>, private val password: String) : Task {

	override fun a(): Any {
		return input.fold(password) { pass, line -> processInstruction(line, pass) }
	}

	override fun b(): Any {
		return input.fold(password) { pass, line -> processInstructionReversed(line, pass) }
	}

	private fun processInstruction(line: String, pass: String): String {
		val split = line.split(" ")
		return when (split[0]) {
			"swap" -> processSwap(split, pass)
			"reverse" -> processReverse(split, pass)
			"rotate" -> processRotate(split, pass)
			"move" -> processMove(split, pass)
			else -> pass
		}
	}

	private fun processInstructionReversed(line: String, pass: String): String {
		val split = line.split(" ")
		return when (split[0]) {
			"swap" -> processSwap(split, pass)
			"reverse" -> processReverse(split, pass)
			"rotate" -> processRotateReversed(split, pass)
			"move" -> processMoveReversed(split, pass)
			else -> pass
		}
	}

	private fun processSwap(split: List<String>, pass: String): String {
		return when (split[1]) {
			"position" -> swapPosition(pass, split[2].toInt(), split[5].toInt())
			"letter" -> swapLetter(pass, split[2][0], split[5][0])
			else -> pass
		}
	}

	private fun processReverse(split: List<String>, pass: String): String {
		val x = split[2].toInt()
		val y = split[4].toInt()
		return reverseSubString(pass, x, y)
	}

	private fun processRotate(split: List<String>, pass: String): String {
		return when (split[1]) {
			"left" -> rotateLeft(pass, split[2].toInt())
			"right" -> rotateRight(pass, split[2].toInt())
			"based" -> rotateBased(pass, split[6][0])
			else -> pass
		}
	}

	private fun processRotateReversed(split: List<String>, pass: String): String {
		return when (split[1]) {
			"left" -> rotateRight(pass, split[2].toInt()) // Reverse left and right
			"right" -> rotateLeft(pass, split[2].toInt()) // Reverse left and right
			"based" -> rotateBasedReversed(pass, split[6][0])
			else -> pass
		}
	}

	private fun processMove(split: List<String>, pass: String): String {
		val x = split[2].toInt()
		val y = split[5].toInt()
		return moveChar(pass, x, y)
	}

	private fun processMoveReversed(split: List<String>, pass: String): String {
		val x = split[2].toInt()
		val y = split[5].toInt()
		return moveCharReversed(pass, x, y)
	}

	private fun swapPosition(pass: String, x: Int, y: Int): String {
		return pass.replaceChar(x, pass[y]).replaceChar(y, pass[x])
	}

	private fun swapLetter(pass: String, x: Char, y: Char): String {
		var password = pass
		val xIndex = password.allIndicesOf(x)
		val yIndex = password.allIndicesOf(y)
		for (ix in xIndex) {
			password = insertCharAtIndex(password, ix, y)
		}
		for (iy in yIndex) {
			password = insertCharAtIndex(password, iy, x)
		}
		return password
	}

	private fun String.allIndicesOf(char: Char): List<Int> {
		val indices = mutableListOf<Int>()
		var i = 0
		while (true) {
			val index = indexOf(char, i)
			if (index == -1) break
			indices.add(index)
			i = index + 1
		}
		return indices
	}

	private fun reverseSubString(pass: String, x: Int, y: Int): String {
		val reversed = pass.substring(x, y + 1).reversed()
		return pass.replaceRange(x, y + 1, reversed)
	}

	private fun rotateLeft(pass: String, steps: Int): String {
		val actualSteps = steps % pass.length
		return pass.substring(actualSteps) + pass.substring(0, actualSteps)
	}

	private fun rotateRight(pass: String, steps: Int): String {
		val actualSteps = steps % pass.length
		return pass.substring(pass.length - actualSteps) + pass.substring(0, pass.length - actualSteps)
	}

	private fun rotateBased(pass: String, x: Char): String {
		val index = pass.indexOf(x)
		val steps = 1 + index + if (index >= 4) 1 else 0
		return rotateRight(pass, steps)
	}

	private fun rotateBasedReversed(pass: String, x: Char): String {

		for (i in pass.indices) {
			val testPassword = pass.substring(i) + pass.substring(0, i)
			val testIndex = testPassword.indexOf(x)
			val rotateSteps = (1 + testIndex + (if (testIndex >= 4) 1 else 0)) % pass.length
			val rotated = rotateRight(testPassword, rotateSteps)

			if (rotated == pass) {
				return testPassword
			}
		}
		return pass
	}

	private fun moveChar(pass: String, x: Int, y: Int): String {
		val xChar = pass[x]
		val passWithoutX = pass.removeCharAt(x)
		return passWithoutX.insertCharAt(y, xChar)
	}

	private fun moveCharReversed(pass: String, x: Int, y: Int): String {
		val yChar = pass[y]
		val passWithoutY = pass.removeCharAt(y)
		return passWithoutY.insertCharAt(x, yChar)
	}

	private fun String.removeCharAt(index: Int): String {
		return this.replaceRange(index, index + 1, "")
	}

	private fun String.insertCharAt(index: Int, char: Char): String {
		return this.substring(0, index) + char + this.substring(index)
	}

	private fun String.replaceChar(index: Int, char: Char): String {
		return this.replaceRange(index, index + 1, char.toString())
	}

	private fun insertCharAtIndex(str: String, index: Int, char: Char): String {
		val builder = StringBuilder(str)
		builder[index] = char
		return builder.toString()
	}
}