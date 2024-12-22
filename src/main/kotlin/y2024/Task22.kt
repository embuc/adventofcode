package y2024

import Task

class Task22(val input: List<String>) : Task {

	override fun a(): Long {
		var sum = 0L
		for (line in input) {
			var secret = line.trim().toLong()
			repeat (2000) {
				secret = transform(secret)
			}
			sum += secret
		}
		return sum
	}

	fun transform(secret: Long): Long {
		var mix = secret xor (secret * 64L)
		var pruned = mix % 16777216L

		mix = pruned xor (pruned / 32L)
		pruned = mix % 16777216L

		mix = pruned xor (pruned * 2048L)
		return mix % 16777216L
	}

	override fun b(): Long {
		val windowMap = mutableMapOf<Long, Long>() // To store window -> value of the last position

		for (line in input) {
			println("start secret: ${line.trim()}")
			var secret = line.trim().toLong()

			var window = 0L // Initialize the sliding window
			var relativeSum = 0L // Cumulative relative difference from the start of the window
			for (i in 1..4) {
				// Perform the 4 steps to initialize the window
				val res1 = secret * 64L
				val mix = secret xor res1
				val pruned = mix % 16777216L

				val divided = pruned / 32L
				val mix2 = pruned xor divided
				val pruned2 = mix2 % 16777216L

				val multiplied = pruned2 * 2048L
				val mix3 = pruned2 xor multiplied
				val newSecret = mix3 % 16777216L

				// Calculate relative difference (from the beginning of the window)
				val relativeDiff = (newSecret % 10) - (secret % 10)
				relativeSum += relativeDiff // Keep a running total of relative differences

				// Update the sliding window
				window = window * 20 + relativeDiff
				secret = newSecret
			}

			val seenNow = mutableSetOf<Long>() // To avoid duplicates in the current iteration
			for (i in 1..1997) {

				// Update the window map only if the window hasn't been seen in this iteration
				if (window !in seenNow) {
					windowMap[window] = relativeSum // Store the cumulative relative difference
					seenNow.add(window)
				}

				// Perform the transformation
				val res1 = secret * 64L
				val mix = secret xor res1
				val pruned = mix % 16777216L

				val divided = pruned / 32L
				val mix2 = pruned xor divided
				val pruned2 = mix2 % 16777216L

				val multiplied = pruned2 * 2048L
				val mix3 = pruned2 xor multiplied
				val newSecret = mix3 % 16777216L

				// Calculate relative difference and update the window
				val relativeDiff = (newSecret % 10) - (secret % 10)
				relativeSum += relativeDiff // Update the cumulative relative difference
				window = (window * 20 + relativeDiff) % (20L * 20 * 20 * 20) // Keep window in range
				secret = newSecret
			}
		}

		// Find the maximum cumulative relative difference for any window pattern
		val maxWindowValue = windowMap.maxOf { it.value }

		println("part 2: $maxWindowValue")

		return maxWindowValue
	}


}