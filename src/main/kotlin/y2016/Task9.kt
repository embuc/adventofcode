package y2016

import Task

//--- Day 9: Explosives in Cyberspace ---
class Task9(val input: String) : Task {

	override fun a(): String {
		val builder = StringBuilder()
		val line = input.replace("\\s".toRegex(), "").toCharArray()
		var ix = 0
		while (ix < line.size) {
			if (line[ix] == '(') {
				val (nbrChars, times, ix2) = extractMarker(line, ix)
				// we are continuing from ix2
				//repeat the next a characters b times
				val extractedString = line.slice(ix2 + 1 until ix2 + nbrChars + 1).joinToString("")
				builder.append(extractedString.repeat(times))
				ix = ix2 + 1 + nbrChars
			} else {
				builder.append(line[ix])
				ix++
			}
		}
		return builder.toString()
	}

	private fun extractMarker(line: CharArray, ix: Int): Triple<Int, Int, Int> {
		val p2 = line.drop(ix + 1).indexOfFirst { it == ')' } + ix + 1
		val (a, b) = line.slice(ix + 1 until p2).joinToString("").split("x").map { it.toInt() }
		return Triple(a, b, p2)
	}

	override fun b(): Long {
		return recursiveCount(input.replace("\\s".toRegex(), "").toCharArray())
	}

	private val memo = mutableMapOf<String, Long>()

	private fun recursiveCount(input: CharArray): Long {
		val key = String(input)
		memo[key]?.let { return it }

		var ix = 0
		var count = 0L
		while (ix < input.size) {
			if (input[ix] == '(') {
				val (nbrChars, times, ix2) = extractMarker(input, ix)
				val extractedString = input.slice(ix2 + 1 until ix2 + nbrChars + 1)
				val array = copyCharArray(extractedString, times)
				val subCount = recursiveCount(array)
				count += subCount
				ix = ix2 + 1 + nbrChars
			} else {
				count++
				ix++
			}
		}
		memo[key] = count
		return count
	}

	fun copyCharArray(charArray: List<Char>, n: Int): CharArray {
		return CharArray(charArray.size * n) {
			charArray[it % charArray.size]
		}
	}

}