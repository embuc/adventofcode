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
		return knotHash(stringInput)
	}

}

fun knotHash(input: String): String {
	val limit = 255
	val inputBytes = input.map { it.code } + listOf(17, 31, 73, 47, 23)
	return calculateHash(inputBytes, limit)
}

private fun calculateHash(input: List<Int>, limit: Int): String {
	val array = (0..limit).mapIndexed { index, _ -> index }.toIntArray()
	var currentPosition = 0
	var skipSize = 0

	// Perform 64 rounds of hash computation
	repeat(64) {
		currentPosition = performRound(input, array, currentPosition, skipSize)
		skipSize += input.size
	}

	return calculateDenseHash(array)
}

private fun performRound(input: List<Int>, array: IntArray, startPosition: Int, initialSkipSize: Int): Int {
	var currentPosition = startPosition
	var skipSize = initialSkipSize

	for (length in input) {
		reverseSubArray(array, currentPosition, length)
		currentPosition = (currentPosition + length + skipSize) % array.size
		skipSize++
	}

	return currentPosition
}

private fun reverseSubArray(array: IntArray, startPos: Int, length: Int) {
	val sublist = IntArray(length) { i ->
		array[(startPos + i) % array.size]
	}

	for (i in 0..<length) {
		val currIx = (startPos + i) % array.size
		array[currIx] = sublist[length - 1 - i]
	}
}

private fun calculateDenseHash(array: IntArray): String {
	val denseHash = IntArray(16) { i ->
		val start = i * 16
		array.slice(start until start + 16).reduce { acc, value -> acc xor value }
	}

	return denseHash.map { it.toString(16).padStart(2, '0') }.joinToString("")
}
