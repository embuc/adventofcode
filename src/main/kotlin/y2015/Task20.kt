package y2015

import Task

//--- Day 20: Infinite Elves and Infinite Houses ---
class Task20(val input: Int) : Task {

	override fun a(): Any {
		// I tried first prime divisors but its 10x slower (still < 1s but this 'sieve' version is much faster)
		val numberOfHouses = 1_000_000
		val houses = IntArray(numberOfHouses)
		for (elf in 1 until numberOfHouses) {
			for (j in elf until numberOfHouses step elf) {
				houses[j] += elf * 10
				if (houses[j] >= input) return j
			}
		}
		return -1
	}

	override fun b(): Any {
		val numberOfHouses = 1_000_000
		val houses = IntArray(numberOfHouses)
		for (elf in 1 until numberOfHouses) {
			for (i in 0 until 50) {
				val houseIndex = elf * (i + 1)
				if (houseIndex >= numberOfHouses) break
				houses[houseIndex] += elf * 11
				if (houses[houseIndex] >= input) return houseIndex
			}
		}
		return -1
	}

}