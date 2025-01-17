package y2017

import Task

//--- Day 15: Dueling Generators ---
class Task15(val input: List<String>) : Task {
	private data class Generator(val name: String, val factor: Long, val start: Long) {
		var value = start
		fun next(): Long {
			value = (value * factor) % 2147483647
			return value
		}
	}

	override fun a(): Any {
		var count = 0
		var parts = input[0].split(" ")
		val genA = Generator(parts[1], 16807, parts[4].toLong())
		parts = input[1].split(" ")
		val genb = Generator(parts[1], 48271, parts[4].toLong())
		for (i in 0 until 40000000) {
			if (genA.next() and 0xFFFF == genb.next() and 0xFFFF) {
				count++
			}
		}
		return count
	}

	override fun b(): Any {
		var count = 0
		var parts = input[0].split(" ")
		val genA = Generator(parts[1], 16807, parts[4].toLong())
		parts = input[1].split(" ")
		val genb = Generator(parts[1], 48271, parts[4].toLong())
		for (i in 0 until 5_000_000) {
			var nextA = genA.next()
			while (nextA % 4 != 0L) {
				nextA = genA.next()
			}
			var nextB = genb.next()
			while (nextB % 8 != 0L) {
				nextB = genb.next()
			}

			if (nextA and 0xFFFF == nextB and 0xFFFF) {
				count++
			}
		}
		return count
	}
}