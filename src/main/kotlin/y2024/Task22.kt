package y2024

import Task
import kotlin.math.max

//--- Day 22: Monkey Market ---
class Task22(val input: List<String>) : Task {

	override fun a(): Long {
		var sum = 0L
		for (line in input) {
			var secret = line.trim().toLong()
			repeat(2000) {
				secret = transform(secret)
			}
			sum += secret
		}
		return sum
	}


	fun transform(secret: Long): Long {
		var mix = secret xor (secret * 64)
		var pruned = mix % 16777216

		mix = pruned xor (pruned / 32)
		pruned = mix % 16777216

		mix = pruned xor (pruned * 2048)
		return mix % 16777216
	}

	override fun b(): Long {
		val dict = mutableMapOf<List<Long>, Long>()
		val seen = mutableSetOf<Pair<List<Long>, String>>()
		for (line in input) {
			var secret = line.trim().toLong()
			val pattern = ArrayList<Long>(4)
			repeat(2000) {
				val newSecret = transform(secret)
				val relativeDiff = (newSecret % 10) - (secret % 10)
				if (pattern.size == 4) {
					pattern.removeAt(0)
				}
				pattern.add(relativeDiff)

				if (pattern.size == 4) {
					val immutablePattern = pattern.toList()
					val price = newSecret % 10

					if (seen.add(Pair(immutablePattern, line))) {
						val number = dict.getOrDefault(immutablePattern, 0)
						dict[immutablePattern] = number + price
					}
				}
				secret = newSecret
			}
		}
		val maxValue = dict.maxOf { it.value }
		return maxValue
	}

}