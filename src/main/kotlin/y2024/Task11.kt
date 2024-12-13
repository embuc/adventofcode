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
		cache2[key]?.let { return it }

		val (first, second) = blinkOnce(stone)
		if (times == 1) {
			return if(second == -1L) 1L else 2L
		}

		val sum = countStoneBlinks(first, times - 1) +
				(if (second != -1L) countStoneBlinks(second, times - 1) else 0)

		cache2[key] = sum
		return sum
	}

	private fun blinkOnce(it: Long): Pair<Long, Long> {
		cache1[it]?.let {return it}
		val stone = it.toString()
		val stonePair = when {
			stone == "0" -> Pair(1L, -1L)
			stone.length % 2 == 0 -> {
				val half = stone.length / 2
				Pair(stone.substring(0, half).toLong(), stone.substring(half).toLong())
			}
			else -> {
				Pair(it * 2024, -1L)
			}
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