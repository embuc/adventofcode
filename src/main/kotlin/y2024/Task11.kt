package y2024

import Task

/* --- Day 11: Plutonian Pebbles --- */
class Task11(val input: String) : Task {

	val cache1 = mutableMapOf<Long, Pair<Long, Long>>()
	val cache2 = mutableMapOf<StoneBlinkKey, Long>()

	data class StoneBlinkKey(val stone: Long, val times: Int)

	fun a(times: Int): Long {
		return input.split(" ").map { it.toLong() }.sumOf { countStoneBlinks(it, times) }
	}

	fun countStoneBlinks(stone: Long, times: Int): Long {
		val key = StoneBlinkKey(stone, times)
		if (cache2.containsKey(key)) {
			return cache2[key]!!
		}
		val once = blinkOnce(stone)
		if (times == 1) {
			if (once.second == -1L) {
				return 1
			} else {
				return 2
			}
		} else {
			var sum = countStoneBlinks(once.first, times - 1)
			if (once.second != -1L) {
				sum += countStoneBlinks(once.second, times - 1)
			}
			cache2[key] = sum
			return sum
		}
	}

	private fun blinkOnce(it: Long): Pair<Long, Long> {
		if (cache1.containsKey(it)) {
			return cache1[it]!!
		}
		val stone = it.toString()
		val stonePair: Pair<Long, Long>
		if (stone == "0") {
			stonePair = Pair(1, -1)
		} else if (stone.length % 2 == 0) {
			val half = stone.length / 2
			stonePair = Pair(stone.substring(0, half).toLong(), stone.substring(half).toLong())
		} else {
			stonePair = Pair(it * 2024, -1)
		}
		cache1[it] = stonePair
		return stonePair
	}

	override fun a(): Any {
		return a(25)
	}

	override fun b(): Any {
		return a(75)
	}
}