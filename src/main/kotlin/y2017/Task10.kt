package y2017

import Task

//--- Day 10: Knot Hash ---
class Task10(val input:List<Int>, private val limit:Int):Task {

	override fun a(): Any {
		val array = (0..limit).mapIndexed {index,_ ->  index}.toIntArray()
		var currentPosition = 0
		var skipSize = 0
		for (length in input) {
			val sublist = IntArray(length) {0}
			for (i in 0..<length){
				val currIx = (currentPosition + i) % array.size
				sublist[length - 1 - i] = array[currIx]
			}
			for (i in 0..<length){
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
		return -1
	}
}