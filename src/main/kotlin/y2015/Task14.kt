package y2015

import Task
import kotlin.math.min

//--- Day 14: Reindeer Olympics ---
class Task14(val input: List<String>, val time: Int) : Task {

	data class Reindeer(val name: String, val speed: Int, val flyTime: Int, val restTime: Int)

	override fun a(): Any {
		val reindeers = mutableListOf<Reindeer>()

		for (line in input) {
			val parts = line.split(" ")
			val name = parts[0]
			val speed = parts[3].toInt()
			val flyTime = parts[6].toInt()
			val restTime = parts[13].toInt()
			reindeers.add(Reindeer(name, speed, flyTime, restTime))
		}

		return reindeers.maxOf{calculateDistance(it)}
	}

	private fun calculateDistance(reindeer: Reindeer): Int {
		val f = time / (reindeer.flyTime + reindeer.restTime)
		val r = time % (reindeer.flyTime + reindeer.restTime)
		val t = (f*reindeer.flyTime + min(reindeer.flyTime, r))*reindeer.speed
		return t
	}

	override fun b(): Int {
		val reindeers = mutableListOf<Reindeer>()

		for (line in input) {
			val parts = line.split(" ")
			val name = parts[0]
			val speed = parts[3].toInt()
			val flyTime = parts[6].toInt()
			val restTime = parts[13].toInt()
			reindeers.add(Reindeer(name, speed, flyTime, restTime))
		}
		// create boolean array for each reindeer that is 'time' in size and populate according to moving/resting logic
		val distances = mutableMapOf<Reindeer, IntArray>()
		for (reindeer in reindeers) {
			val array = IntArray(time+1)
			for (t in 1..time) {
				array[t] = calcDistance(reindeer, t)
			}
			distances[reindeer] = array
		}
		val points = mutableMapOf<Reindeer, Int>()
		for (i in 1..time){
			val leaders = findLeaders(distances, i)
			for (reindeer in leaders) {
				points[reindeer] = points.getOrDefault(reindeer, 0) + 1
			}
		}
		return points.maxOf { it.value }
	}

	private fun calcDistance(reindeer: Reindeer, t: Int): Int {
		val f = t / (reindeer.flyTime + reindeer.restTime)
		val r = t % (reindeer.flyTime + reindeer.restTime)
		val d = (f*reindeer.flyTime + min(reindeer.flyTime, r))*reindeer.speed
		return d
	}

	private fun findLeaders(reindeers: MutableMap<Reindeer, IntArray>, i: Int): List<Reindeer> {
		var max = 0
		val leaders = mutableListOf<Reindeer>()
		for (reindeer in reindeers) {
			if (reindeer.value[i] > max) {
				max = reindeer.value[i]
				leaders.clear()
				leaders.add(reindeer.key)
			}
			else if (reindeer.value[i] == max) {
				leaders.add(reindeer.key)
			}
		}
		return leaders
	}
}