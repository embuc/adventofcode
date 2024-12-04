package y2024

import Task

/* --- Day 3: Mull It Over --- */
class Task3(val input:String):Task {

	override fun a(): Any {
		val muls = findMuls(input)
		return getSum(muls)
	}

	private fun getSum(muls: List<Pair<Int, Int>>): Int {
		var sum = 0
		for (mul in muls) {
			sum += mul.first * mul.second
		}
		return sum
	}

	private fun findMuls(input: String): List<Pair<Int, Int>> {
		val muls = mutableListOf<Pair<Int, Int>>()
		val mulRegex = Regex("mul\\((\\d+),(\\d+)\\)")
		val matches = mulRegex.findAll(input)
		for (match in matches) {
			val (first, second) = match.destructured
			val pair = Pair(first.toInt(), second.toInt())
			muls.add(pair)
		}
		return muls
	}

	override fun b(): Any {
		val filterRegexp = Regex("(do_not\\(\\)|don't\\(\\)).*?(do\\(\\)|$)")
		val inputNoLNs = input.replace("\n", "")
		val filtered = inputNoLNs.replace(filterRegexp, "")
		val muls = findMuls(filtered)
		return getSum(muls)
	}
}