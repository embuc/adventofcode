package y2016

import Task
import java.util.*

//--- Day 19: An Elephant Named Joseph ---
class Task19(val input: Int) : Task {
	override fun a(): Any {
		val elves = LinkedList<Int>()
		for (i in 1..input) {
			elves.add(i)
		}
		var rest = false
		while (elves.size > 1) {
			val it = elves.iterator()
			if (rest) {
				it.next()
				it.remove()
				rest = false
			}
			while (it.hasNext()) {
				it.next()
				if (it.hasNext()) {
					it.next()
					it.remove()
				} else {
					rest = true
					break
				}
			}
		}
		return elves.first
	}

	override fun b(): Any {
		// Brute force with list took >4mins for real input :( but this pow3 relation is just so cheeky
//		for (n in 1..85) {
//			// print to see relation
//			val result = solveForN(n)
//			println("n=$n: $result")
//		}
		return solveForN(input)
	}

	private fun solveForN(n: Int): Int {
		var pow3 = 1
		while (pow3 * 3 <= n) {
			pow3 *= 3
		}

		// If n is exactly a power of 3, return n
		if (n == pow3) {
			return n
		}

		// use the formula: n - p + max(0, n - 2p)
		// where p is the largest power of 3 <= n
		return n - pow3 + maxOf(0, n - 2 * pow3)
	}
}