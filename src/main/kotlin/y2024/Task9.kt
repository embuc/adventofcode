package y2024

import Task

/* --- Day 9: Disk Fragmenter --- */
class Task9(val input: String) : Task {

	override fun a(): Any {
//		println("input: $input")
		val expanded = expandFS(input)
		val defragmented = defragment(expanded)
//		println("defragmented: $defragmented")
		return countChecksum(defragmented)
	}

	private fun countChecksum(defragmented: List<Int>): Long {
		var checksum = 0L
		for (i in defragmented.indices) {
			val id = defragmented[i]
			if (id == -1) {
				break
			}
			checksum += id * i
		}
		return checksum
	}

	private fun defragment(expanded: List<Int>): List<Int> {
		val defragmented = expanded.toIntArray()
		println("defragmented: ${defragmented.size}")
		var indexA = findNextForward(defragmented, 0)
		var indexB = findNextBackward(defragmented, defragmented.size - 1)
		while (indexA<indexB) {
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

//	private fun expandFS(input: String): List<Int> {
//		// 12345 ->  [0,-1,-1,1,1,1,-1,-1,-1,-1,2,2,2,2,2]
//		// [0,0,-1,-1,1,-1,2,...,11,-1,12...]
//		// iterate and every odd index is the file part and every even is the empty spaces ('.')
//		val list = mutableListOf<Int>()
//		var index = 0
//		var odd = true
//		for (char in input) {
//			val num = char.toString().toInt()
//			if (odd) {
//				for (i in 0 until num) {
//					list.add(index)
//				}
//				index++
//			} else {
//				for (i in 0 until num) {
//					list.add(-1)
//				}
//			}
//			odd = !odd
//		}
//		return list
//	}
	private fun expandFS(input: String): List<Int> {
		var index = 0
		return input.map { it.toString().toInt() }
			.flatMap { num ->
				if (index++ % 2 == 0) List(num) { index - 1 } else List(num) { -1 }
			}
	}


	override fun b(): Any {
		//		00...111...2...333.44.5555.6666.777.888899
		//		0099.111...2...333.44.5555.6666.777.8888..
		//		0099.1117772...333.44.5555.6666.....8888..
		//		0099.111777244.333....5555.6666.....8888..
		//		00992111777.44.333....5555.6666.....8888..
		println("input: $input")
		val expanded = expandFS(input)
		val defragmented = defragmentB(expanded)
//		println("defragmented: $defragmented")
		return countChecksum(defragmented)
		return 0
	}

	fun defragmentB(expanded: List<Int>): List<Int> {
		val defragmented = expanded.toIntArray()
		println("defragmented: ${defragmented.size}")
		var indexA = findNextForward(defragmented, 0)
		var indexB = findNextBackward(defragmented, defragmented.size - 1)
		while (indexA<indexB) {
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
}