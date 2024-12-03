package y2024

import Task
/* --- Day 2: Red-Nosed Reports ---
* The levels are either all increasing or all decreasing.
* Any two adjacent levels differ by at least one and at most three.
*/
class Task2(val input:List<String>):Task {

	override fun a(): Any {
		val lists = ArrayList<List<Int>>();
		for (line in input) {
			val list = line.split(" ").map { it.toInt() }
			lists.add(list)
		}
		var count = 0
		for (list in lists) {
			if (isIncreasing(list, 0) || isDecreasing(list, 0)) {
				count++
			}
		}
		return count
	}

	override fun b(): Any {
		val lists = ArrayList<List<Int>>();
		for (line in input) {
			val list = line.split(" ").map { it.toInt() }
			lists.add(list)
		}
		var count = 0
		for (list in lists) {
			if (isIncreasing(list, 1) || isDecreasing(list, 1) || isIncreasing(list.reversed(), 1)
				|| isDecreasing(list.reversed(), 1)) {
				count++
			}
		}
		return count
	}

	private fun isDecreasing(list: List<Int>, allowedFailures: Int): Boolean {
		var failures = 0
		var prev = list[0]
		var next = list[1]
		for (i in 1 until list.size) {
			if ((next >= prev) || (prev - next > 3)) {
				failures++
				if (i + 1 < list.size) {
					next = list[i + 1]
				} else {
					break
				}
			}else {
				prev = next
				if (i + 1 < list.size) {
					next = list[i + 1]
				} else {
					break
				}
			}
		}
		return failures <= allowedFailures
	}

	private fun isIncreasing(list: List<Int>, allowedFailures: Int): Boolean {
		var failures = 0
		var prev = list[0]
		var next = list[1]
		for (i in 1 until list.size) {
			if ((next <= prev) || (next - prev > 3)) {
				failures++
				if (i + 1 < list.size) {
					next = list[i + 1]
				} else {
					break
				}
			} else {
				prev = next
				if (i + 1 < list.size) {
					next = list[i + 1]
				} else {
					break
				}
			}
		}
		return failures <= allowedFailures
	}

}