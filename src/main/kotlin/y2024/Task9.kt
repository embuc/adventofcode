package y2024

import Task

/* --- Day 9: Disk Fragmenter --- */
class Task9(val input: String) : Task {

	override fun a(): Any {
		val expanded = expandFS(input)
		val defragmented = defragment(expanded)
		return countChecksum(defragmented)
	}

	private fun countChecksum(defragmented: List<Int>): Long {
		var checksum = 0L
		for (i in defragmented.indices) {
			val id = defragmented[i]
			if (id == -1) {
				continue
			}
			checksum += id * i
		}
		return checksum
	}

	private fun defragment(expanded: List<Int>): List<Int> {
		val defragmented = expanded.toIntArray()
		var indexA = findNextForward(defragmented, 0)
		var indexB = findNextBackward(defragmented, defragmented.size - 1)
		while (indexA < indexB) {
			val a = defragmented[indexA]
			val b = defragmented[indexB]
			//replace
			defragmented[indexA] = defragmented[indexB]
			defragmented[indexB] = -1

			indexA = findNextForward(defragmented, indexA)
			indexB = findNextBackward(defragmented, indexB)
		}

		return defragmented.toList();
	}

	private fun findNextBackward(expanded: IntArray, indexB: Int): Int {
		for (i in indexB downTo 0) {
			if (expanded[i] != -1) {
				return i
			}
		}
		return -1
	}

	private fun findNextForward(expanded: IntArray, indexA: Int): Int {
		for (i in indexA until expanded.size) {
			if (expanded[i] == -1) {
				return i
			}
		}
		return -1
	}

	private fun expandFS(input: String): List<Int> {
		val list = mutableListOf<Int>()
		var index = 0
		var odd = true
		for (char in input) {
			val num = char.toString().toInt()
			if (odd) {
				repeat(num) { list.add(index) }
				index++
			} else {
				repeat(num) { list.add(-1) }
			}
			odd = !odd
		}
		return list
	}

	override fun b(): Any {
		//		00...111...2...333.44.5555.6666.777.888899
		//		0099.111...2...333.44.5555.6666.777.8888..
		//		0099.1117772...333.44.5555.6666.....8888..
		//		0099.111777244.333....5555.6666.....8888..
		//		00992111777.44.333....5555.6666.....8888..
		val expanded = expandFS(input)
		val defragmented = defragmentB(expanded)
		return countChecksum(defragmented)
	}

	fun defragmentB(expanded: List<Int>): List<Int> {
		val defragmented = expanded.toIntArray()
		var indexB = findNextBackwardB(defragmented, defragmented.size - 1)
		while (indexB.first > indexB.second) {
			//we have a candidate on the right side, search from left to check if we have a free slot
			val indexA = findNextForwardB(defragmented, 0, indexB.second, indexB.first)
			if (indexA != -1) {
				for (i in 0 until indexB.second) {
					defragmented[indexA + i] = defragmented[indexB.first - i]
					defragmented[indexB.first - i] = -1
				}
			}

			indexB = findNextBackwardB(defragmented, indexB.first - indexB.second)
		}
		return defragmented.toList();
	}

	private fun findNextBackwardB(defragmented: IntArray, i: Int): Pair<Int, Int> {
		for (index in i downTo 0) {
			if (defragmented[index] != -1) {
				//check backward how many of this int we have in a block
				var count = 0
				for (j in index downTo 0) {
					if (defragmented[j] == defragmented[index]) {
						count++
					} else {
						break
					}
				}
				return Pair(index, count)
			}
		}
		return Pair(-1, -1)
	}

	private fun findNextForwardB(defragmented: IntArray, i: Int, size: Int, limit: Int): Int {
		for (index in 0 until limit) {
			if (defragmented[index] == -1) {
				//check forward if we have 'size' number of free slots
				if (index + size < limit) {
					var found = true
					for (j in 0 until size) {
						if (defragmented[index + j] != -1) {
							found = false
							break
						}
					}
					if (found) {
						return index
					}
				}
			}
		}
		return -1
	}
}