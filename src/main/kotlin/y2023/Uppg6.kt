package y2023

import kotlin.system.measureTimeMillis

//Time:        59     79     65     75
//Distance:   597   1234   1032   1328
class Race(val time: Long, val distance: Long) {}

val races = listOf(Race(59, 597), Race(79, 1234), Race(65, 1032), Race(75, 1328))
val races2 = listOf(Race(59796575, 597123410321328L))

class Uppg6 {

	fun b() {
		val executionTime = measureTimeMillis {
			var result = 1L;
			for (race in races2) {
				result *= findWays(race);
			}
			println(result)
		}
		println("Execution Time: $executionTime ms")
	}

	fun a() {
		var result = 1L;
		for (race in races) {
			result *= findWays(race);
		}
		println(result)
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