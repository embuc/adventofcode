package y2015

import Task
import kotlin.math.min

//--- Day 14: Reindeer Olympics ---
class Task14(val input: List<String>, val time: Int) : Task {

	data class Reindeer(val speed: Int, val flyTime: Int, val restTime: Int)

	override fun a(): Any {
		val reindeers = mutableListOf<Reindeer>()

		for (line in input) {
			val parts = line.split(" ")
			val speed = parts[3].toInt()
			val flyTime = parts[6].toInt()
			val restTime = parts[13].toInt()
			reindeers.add(Reindeer(speed, flyTime, restTime))
		}

		var max = 0
		for (reindeer in reindeers) {
			val curr = calculateDistance(reindeer)
			if(curr > max)
				max = curr
		}
		return max
	}

	private fun calculateDistance(reindeer: Reindeer): Int {
		val f = time / (reindeer.flyTime + reindeer.restTime)
		val r = time % (reindeer.flyTime + reindeer.restTime)
		val t = (f*reindeer.flyTime + min(reindeer.flyTime, r))*reindeer.speed
		return t
	}

	override fun b(): Any {
		return 0
	}
}