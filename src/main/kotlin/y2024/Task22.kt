package y2024

import Task

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
		val seen = mutableSetOf<Pair<List<Long>, String>>() // seen per pattern and line/buyer
		for (line in input) {
			var secret = line.trim().toLong()
			val pattern = mutableListOf<Long>()
			repeat(2000) {
				val newSecret = transform(secret)
				val relativeDiff = (newSecret % 10) - (secret % 10)
				if (pattern.size == 4) {
					pattern.removeAt(0)
				}
				pattern.add(relativeDiff)

				if (pattern.size == 4) {
					val price = newSecret % 10
					if (!seen.contains(Pair(pattern, line))) {
						val p2 = pattern.toList()
						val number = dict.getOrDefault(p2, 0)
						dict[p2] = number + price
						seen.add(Pair(p2, line))
					}
				}
				secret = newSecret
			}
		}
		val maxEntry = dict.maxBy { it.value }
		val (keyPair, value) = maxEntry
		println("Max entry: Key = ${keyPair}, Value = $value.  ")

		return  value
	}

}