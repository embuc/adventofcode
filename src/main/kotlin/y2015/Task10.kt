package y2015

import Task

//--- Day 10: Elves Look, Elves Say ---
class Task10(val input: Pair<String, Int>) : Task {

	override fun a(): Any {
		return transformDigits(input.first, input.second, 1).length;
	}

	private fun transformDigits(input: String, times: Int, i: Int): String {
		if(i > times) return input
		var transformed = StringBuilder()
		var count = 0
		var curr:Char
		for(ix in input.indices) {
			curr = input[ix]
			count++
			if(ix + 1 < input.length && input[ix+1] != curr || ix + 1 == input.length){
				transformed.append("$count$curr")
				count = 0
			}
		}
		return transformDigits(transformed.toString(), times, i + 1)
	}

	override fun b(): Any {
		TODO("Not yet implemented")
	}


}