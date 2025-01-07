package y2016

import Task

//--- Day 15: Timing is Everything ---
class Task15(val input:List<String>) :Task {

	private data class Disc(val totPos:Int, var currPos: Int)

	override fun a(): Any {
		val discs = parseDiscs()
		return processDiscs(100_000, discs)
	}
	override fun b(): Any {
		val discs = parseDiscs()
		discs.add(Disc(11, 0))
		return processDiscs(10_000_000, discs)
	}

	private fun processDiscs(n: Int, discs: MutableList<Disc>): Int {
		for (i in 0..n) {
			var all = true
			for ((ix, disc) in discs.withIndex()) {
				if ((disc.currPos + (ix + 1) + i) % disc.totPos != 0) {
					all = false
				}
			}
			if (all) {
				return i
			}
		}
		return -1
	}

	private fun parseDiscs(): MutableList<Disc> {
		val discs = mutableListOf<Disc>()
		for (line in input) {
			val parts = line.split(" ")
			discs.add(Disc(parts[3].toInt(), parts[11].replace(".", "").toInt()))
		}
		return discs
	}
}