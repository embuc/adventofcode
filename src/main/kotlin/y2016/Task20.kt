package y2016

import Task

// --- Day 20: Firewall Rules ---
class Task20(val input: List<String>) : Task {

	override fun a(): Any {
		val ranges = mutableListOf<LongRange>()
		for (i in input) {
			val split = i.trim().split("-")
			ranges.add(split[0].toLong()..split[1].toLong())
		}
		ranges.sortBy { it.first }
		var lastTotal = 0L
		var previousLast = 4294967295L
		for (r in ranges) {
			if (r.first > previousLast && r.first > lastTotal + 1) {
				return lastTotal + 1
			}
			previousLast = r.last
			if (previousLast > lastTotal) {
				lastTotal = previousLast
			}
		}
		return -1
	}


	override fun b(): Any {
		val ranges = mutableListOf<LongRange>()
		for (i in input) {
			val split = i.trim().split("-")
			ranges.add(split[0].toLong()..split[1].toLong())
		}
		ranges.sortBy { it.first }
		var lastTotal = 0L
		var last = 4294967296L
		var count = 0L
		for (r in ranges) {
			if (r.first > last + 1 && r.first > lastTotal + 1) {
				count += r.first - (lastTotal + 1)
			}
			last = r.last
			if (last > lastTotal) {
				lastTotal = last
			}
		}
		return count
	}
}