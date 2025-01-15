package y2017

import Task

//--- Day 10: Knot Hash ---
class Task10(val stringInput: String, private val limit: Int) : Task {

	override fun a(): Any {
		val input = stringInput.split(",").map { it.toInt() }
		val array = (0..limit).mapIndexed { index, _ -> index }.toIntArray()
		var currentPosition = 0
		var skipSize = 0
		for (length in input) {
			val sublist = IntArray(length) { 0 }
			for (i in 0..<length) {
				val currIx = (currentPosition + i) % array.size
				sublist[length - 1 - i] = array[currIx]
			}
			for (i in 0..<length) {
				val currIx = (currentPosition + i) % array.size
				array[currIx] = sublist[i]
			}
			currentPosition += length + skipSize
			currentPosition %= array.size
			skipSize++
		}
		return array[0] * array[1]
	}

	override fun b(): Any {
		val input = stringInput.map { it.code } + listOf(17, 31, 73, 47, 23)
		val array = (0..limit).mapIndexed { index, _ -> index }.toIntArray()
		var currentPosition = 0
		var skipSize = 0
		repeat(64) {
			for (length in input) {
				val sublist = IntArray(length) { 0 }
				for (i in 0..<length) {
					val currIx = (currentPosition + i) % array.size
					sublist[length - 1 - i] = array[currIx]
				}
				for (i in 0..<length) {
					val currIx = (currentPosition + i) % array.size
					array[currIx] = sublist[i]
				}
				currentPosition += length + skipSize
				currentPosition %= array.size
				skipSize++
			}
		}

		//find dense hash 16 * 16 numbers
		val denseHash = IntArray(16) { 0 }
		for (i in 0 until 16) {
			val start = i * 16
			val end = start + 16
			val sublist = array.slice(start until end)
			denseHash[i] = sublist.reduce { acc, i -> acc xor i }
		}
		return denseHash.map { it.toString(16).padStart(2, '0') }.joinToString("")
	}
}