package y2017

import Task
import kotlin.math.abs

//--- Day 20: Particle Swarm ---
class Task20(val input:List<String>) : Task {
	private data class Particle(
		var p: Triple<Long, Long, Long>,
		var v: Triple<Long, Long, Long>,
		var a: Triple<Long, Long, Long>
	)

	override fun a(): Any {
		val particles = parseParticles()
		repeat(400) {
			for (p in particles) {
				p.v = Triple(p.v.first + p.a.first, p.v.second + p.a.second, p.v.third + p.a.third)
				p.p = Triple(p.p.first + p.v.first, p.p.second + p.v.second, p.p.third + p.v.third)
			}
		}
		return particles.indexOf(particles.minBy { abs(it.p.first) + abs(it.p.second) + abs(it.p.third) })
	}

	override fun b(): Any {
		val particles = parseParticles()
		repeat(400) {
			for (p in particles) {
				p.v = Triple(p.v.first + p.a.first, p.v.second + p.a.second, p.v.third + p.a.third)
				p.p = Triple(p.p.first + p.v.first, p.p.second + p.v.second, p.p.third + p.v.third)
			}
			val positions = particles.map { it.p }
			val duplicates = positions.groupingBy { it }.eachCount().filter { it.value > 1 }.keys
			particles.removeIf { it.p in duplicates }
		}
		return particles.size
	}

	private fun parseParticles(): MutableList<Particle> {
		val particles = mutableListOf<Particle>()
		for (l in input) {
			val parts = l.split(", ").map { it.split("=") }
			val p = parts[0][1].split(",").map { it.replace("<", "").replace(">", "").toLong() }
			val v = parts[1][1].split(",").map { it.replace("<", "").replace(">", "").toLong() }
			val a = parts[2][1].split(",").map { it.replace("<", "").replace(">", "").toLong() }
			particles.add(Particle(Triple(p[0], p[1], p[2]), Triple(v[0], v[1], v[2]), Triple(a[0], a[1], a[2])))
		}
		return particles
	}
}