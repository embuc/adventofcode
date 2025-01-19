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
			particles.forEach { p ->
				p.v += p.a
				p.p += p.v
			}
		}
		return particles.indexOf(particles.minBy { it.p.manhattanDistance() })
	}

	override fun b(): Any {
		val particles = parseParticles()
		repeat(400) {
			particles.forEach { p ->
				p.v += p.a
				p.p += p.v
			}
			val duplicates = particles.groupBy { it.p }.filter { it.value.size > 1 }.keys
			particles.removeAll { it.p in duplicates }
		}
		return particles.size
	}

	private fun parseParticles(): MutableList<Particle> {
		return input.map { line ->
			val (p, v, a) = line.split(", ").map { it.extractTriple() }
			Particle(p, v, a)
		}.toMutableList()
	}

	private fun String.extractTriple(): Triple<Long, Long, Long> {
		return this.split("=")[1].removeSurrounding("<", ">").split(",").map { it.toLong() }.let {
			Triple(it[0], it[1], it[2])
		}
	}

	private fun Triple<Long, Long, Long>.manhattanDistance() = abs(first) + abs(second) + abs(third)

	private operator fun Triple<Long, Long, Long>.plus(other: Triple<Long, Long, Long>) =
		Triple(first + other.first, second + other.second, third + other.third)
}