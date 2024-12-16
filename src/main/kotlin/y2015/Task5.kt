package y2015

import Task

/*--- Day 5: Doesn't He Have Intern-Elves For This? ---*/
class Task5(val input: List<String>) : Task {

	override fun a(): Any {
		var count = 0
		val p1 = Regex(".*[aeiou]+.*[aeiou]+.*[aeiou]+.*")
		val p2 = Regex("(.)\\1")
		val p3 = Regex("^(?!.*(ab|cd|pq|xy)).*$")
		for (s in input) {
			count += if(p1.containsMatchIn(s) && p2.containsMatchIn(s) && p3.containsMatchIn(s)) 1 else 0
		}
		return count
	}

	override fun b(): Any {
		TODO("Not yet implemented")
	}
}