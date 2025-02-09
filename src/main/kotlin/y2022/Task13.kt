package y2022

import Task

//--- Day 13: Distress Signal ---
class Task13(val input: List<String>) : Task {

	override fun a(): Any {
		var i = 0
		var pairIndex = 1  // 1-based indexing! (easy to overlook in description)
		var sum = 0

		while (i < input.size - 1) {
			if (input[i].isEmpty()) {
				i++
				continue
			}

			val line1 = input[i]
			val line2 = input[i + 1]

			val comparisonResult = compare(line1, line2)
			if (comparisonResult == -1) {
				sum += pairIndex
			}

			pairIndex++
			i += 2
			// Skip empty line between pairs if it exists
			if (i < input.size && input[i].isEmpty()) {
				i++
			}
		}

		return sum
	}

	private fun compare(group1: String, group2: String): Int {
		// Parse the input strings into lists or integers
		val left = parsePacket(group1)
		val right = parsePacket(group2)

		// Compare the parsed structures
		return compareValues(left, right)
	}

	private fun parsePacket(packet: String): Any {
		return if (packet.startsWith('[')) {
			// Parse as a list
			parseList(packet)
		} else {
			// Parse as an integer
			packet.toInt()
		}
	}

	private fun parseList(listStr: String): List<Any> {
		val list = mutableListOf<Any>()
		var current = StringBuilder()
		var depth = 0

		for (i in 1 until listStr.length - 1) {
			val char = listStr[i]
			when {
				char == '[' -> {
					depth++
					current.append(char)
				}
				char == ']' -> {
					depth--
					current.append(char)
				}
				char == ',' && depth == 0 -> {
					list.add(parsePacket(current.toString()))
					current = StringBuilder()
				}
				else -> current.append(char)
			}
		}

		if (current.isNotEmpty()) {
			list.add(parsePacket(current.toString()))
		}

		return list
	}

	private fun compareValues(left: Any, right: Any): Int {
		return when {
			left is Int && right is Int -> {
				when {
					left < right -> -1
					left > right -> 1
					else -> 0
				}
			}
			left is List<*> && right is List<*> -> {
				compareLists(left as List<Any>, right as List<Any>)
			}
			left is Int -> {
				compareValues(listOf(left), right)
			}
			right is Int -> {
				compareValues(left, listOf(right))
			}
			else -> 0
		}
	}

	private fun compareLists(left: List<Any>, right: List<Any>): Int {
		for (i in 0 until minOf(left.size, right.size)) {
			val result = compareValues(left[i], right[i])
			if (result != 0) {
				return result
			}
		}
		return when {
			left.size < right.size -> -1
			left.size > right.size -> 1
			else -> 0
		}
	}

	override fun b(): Any {
		val allPackets = mutableListOf<String>()

		for (line in input) {
			if (line.isNotEmpty()) {
				allPackets.add(line)
			}
		}

		// Add divider packets
		val divider1 = "[[2]]"
		val divider2 = "[[6]]"
		allPackets.add(divider1)
		allPackets.add(divider2)

		// Sort the packets using comparison function
		allPackets.sortWith { a, b -> compare(a, b) }

		// Find indices of divider packets (1-based indexing)
		val index1 = allPackets.indexOf(divider1) + 1
		val index2 = allPackets.indexOf(divider2) + 1
		return index1 * index2
	}
}