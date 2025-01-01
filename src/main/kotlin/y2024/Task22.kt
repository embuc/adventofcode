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


//	fun transform(secret: Long): Long {
//		var mix = secret xor (secret * 64)
//		var pruned = mix % 16777216
//
//		mix = pruned xor (pruned / 32)
//		pruned = mix % 16777216
//
//		mix = pruned xor (pruned * 2048)
//		return mix % 16777216
//	}

	fun transform(secret: Long): Long {
		// Instead of (secret * 64), use (secret shl 6)
		var mix = secret xor (secret shl 6)
		var pruned = mix and 0xFFFFFF

		// Instead of (pruned / 32), use (pruned shr 5)
		mix = pruned xor (pruned shr 5)
		pruned = mix and 0xFFFFFF

		// Instead of (pruned * 2048), use (pruned shl 11)
		mix = pruned xor (pruned shl 11)
		return mix and 0xFFFFFF
	}

	private fun encode4(d0: Int, d1: Int, d2: Int, d3: Int): Int {
		// Shift each difference into the range [0..18] by adding 9
		// Then pack them into 4 "slots" of 5 bits each.
		//   e0 goes to bits [15..19],
		//   e1 goes to bits [10..14],
		//   e2 goes to bits [5..9],
		//   e3 goes to bits [0..4].
		val e0 = d0 + 9
		val e1 = d1 + 9
		val e2 = d2 + 9
		val e3 = d3 + 9
		return (e0 shl 15) or (e1 shl 10) or (e2 shl 5) or e3
	}

	override fun b(): Long {
		// Convert each line to a long once, to avoid repeated .trim() and .toLong() calls.
		val linesAsLongs = input.map { it.trim().toLong() }
		// We'll store the sum of "price" in this map.  Key is the encoded pattern (Int).
		val patternSums = mutableMapOf<Int, Long>()
		val seen = mutableSetOf<Pair<Int, Int>>()

		for ((lineIndex, initialSecret) in linesAsLongs.withIndex()) {
			var secret = initialSecret

			// Rolling buffer of diffs
			val diffs = IntArray(4)
			var idx = 0

			repeat(2000) { i ->
				val newSecret = transform(secret)
				val relativeDiff = ((newSecret % 10) - (secret % 10)).toInt()

				// Put the diff in a circular array
				diffs[idx] = relativeDiff
				idx = (idx + 1) % 4

				// Only after we've accumulated 4 diffs do we have a "pattern"
				if (i >= 3) {
					// Rebuild the 4-diff pattern in the correct order
					val d0 = diffs[idx]
					val d1 = diffs[(idx + 1) % 4]
					val d2 = diffs[(idx + 2) % 4]
					val d3 = diffs[(idx + 3) % 4]

					// Encode the 4 differences into a single Int
					val patternKey = encode4(d0, d1, d2, d3)

					// The "price" is just (newSecret % 10)
					val price = (newSecret % 10)

					if (seen.add(patternKey to lineIndex)) {
						// If not seen before, add to the sums
						val oldSum = patternSums.getOrDefault(patternKey, 0L)
						patternSums[patternKey] = oldSum + price
					}
				}

				secret = newSecret
			}
		}

		return patternSums.values.maxOrNull() ?: 0L

//      This version bellow was the first version, it reads better but 50% slower
//		val dict = mutableMapOf<List<Long>, Long>()
//		val seen = mutableSetOf<Pair<List<Long>, String>>()
//		for (line in input) {
//			var secret = line.trim().toLong()
//			val pattern = ArrayList<Long>(4)
//			repeat(2000) {
//				val newSecret = transform(secret)
//				val relativeDiff = (newSecret % 10) - (secret % 10)
//				if (pattern.size == 4) {
//					pattern.removeAt(0)
//				}
//				pattern.add(relativeDiff)
//
//				if (pattern.size == 4) {
//					val immutablePattern = pattern.toList()
//					val price = newSecret % 10
//
//					if (seen.add(Pair(immutablePattern, line))) {
//						val number = dict.getOrDefault(immutablePattern, 0)
//						dict[immutablePattern] = number + price
//					}
//				}
//				secret = newSecret
//			}
//		}
//		val maxValue = dict.maxOf { it.value }
//		return maxValue
	}

}