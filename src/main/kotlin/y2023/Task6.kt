package y2023

import Task
import kotlin.system.measureTimeMillis
// --- Day 6: Wait For It ---
object Task6: Task {

	//Time:        59     79     65     75
	//Distance:   597   1234   1032   1328
	data class Race(val time: Long, val distance: Long) {}

	val races = listOf(Race(59, 597), Race(79, 1234), Race(65, 1032), Race(75, 1328))
	val races2 = listOf(Race(59796575, 597123410321328L))

	override fun b(): Long {
		val executionTime = measureTimeMillis {
			var result = 1L;
			for (race in races2) {
				result *= findWays(race);
			}
		}
		return executionTime
	}

	override fun a(): Long {
		var result = 1L;
		for (race in races) {
			result *= findWays(race);
		}
		return result
	}

	private fun findWays(race: Race): Long {
		var ways = 0L
		for (i in 1..race.time) {
			if (i * (race.time - i) > race.distance) {
				ways++
			}
		}
		return ways
	}
}