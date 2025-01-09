package y2016

import Task

class Task21(val input: List<String>, val password: String) : Task {

	override fun a(): Any {
		var pass = password
		for (line in input) {
			println(line)
			pass = processInstruction(line, pass)
			println(pass)
		}
		return pass
	}

	private fun swapPosition(pass: String, x: Int, y: Int): String {
		val xChar = pass[x]
		val yChar = pass[y]
		return pass.replaceRange(x, x + 1, yChar.toString()).replaceRange(y, y + 1, xChar.toString())
	}

	private fun processInstruction(line: String, pass: String): String {
		var password = pass
		val split = line.split(" ")
		return when (split[0]) {
			"swap" -> {
				when (split[1]) {
					"position" -> {
						val x = split[2].toInt()
						val y = split[5].toInt()
						return swapPosition(password, x, y)
					}

					"letter" -> {
						val x = split[2][0]
						val y = split[5][0]
						val xIndex = mutableListOf<Int>()
						var i = 0
						while (password.contains(x)) {
							val ix = password.indexOf(x, i)
							if (ix == -1) break
							xIndex.add(ix)
							i = ix + 1
						}
						val yIndex = mutableListOf<Int>()
						i = 0
						while (password.contains(y)) {
							val ix = password.indexOf(y, i)
							if (ix == -1) break
							yIndex.add(ix)
							i = ix + 1
						}
						for (ix in xIndex) {
							password = insertCharAtIndex(password, ix, y)
						}
						for (iy in yIndex) {
							password = insertCharAtIndex(password, iy, x)
						}
						return password

					}
					else -> password
				}
			}
			"reverse" -> {
				val x = split[2].toInt()
				val y = split[4].toInt()
				val reversed = password.substring(x, y + 1).reversed()
				return password.replaceRange(x, y + 1, reversed)
			}
			"rotate" -> {
				when (split[1]) {
					"left" -> {
						val steps = split[2].toInt()
						val rotated = password.substring(steps) + password.substring(0, steps)
						return rotated
					}
					"right" -> {
						val steps = split[2].toInt()
						val rotated = password.substring(password.length - steps) + password.substring(0, password.length - steps)
						return rotated
					}
					"based" -> {
						val x = split[6][0]
						val index = password.indexOf(x)
						val steps = 1 + index + if (index >= 4) 1 else 0
						val rotated = password.substring(password.length - steps%password.length) +
								password.substring(0, password.length - steps%password.length)
						return rotated
					}
					else -> password
				}
			}
			"move" -> {
				val x = split[2].toInt()
				val y = split[5].toInt()
				val xChar = password[x]
				val passWithoutX = password.replaceRange(x, x + 1, "")
				return passWithoutX.substring(0, y) + xChar + passWithoutX.substring(y)
			}

			else -> password
		}

	}

	fun insertCharAtIndex(str: String, index: Int, char: Char): String {
		if (index < 0 || index > str.length) {
			throw IndexOutOfBoundsException("Index out of bounds")
		}

		val builder = StringBuilder(str)
		builder[index] = char
		return builder.toString()
	}

	override fun b(): Any {
		return 0
	}
}