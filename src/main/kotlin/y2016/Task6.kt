package y2016

import Task

//--- Day 6: Signals and Noise ---
class Task6(val input: List<String>) : Task {

	override fun a(): Any {
		return processColumns(compareByDescending<Map.Entry<Char, Int>> { it.value }.thenBy { it.key })
	}

	override fun b(): Any {
		return processColumns(compareBy<Map.Entry<Char, Int>> { it.value }.thenBy { it.key })
	}

	private fun processColumns(comparator: Comparator<Map.Entry<Char, Int>>): String {
		val cols = Array(input[0].length) { StringBuilder() }

		input.forEach { line ->
			// forEachIndexed good stuff!
			line.toCharArray().forEachIndexed { index, char ->
				cols[index].append(char)
			}
		}

		return cols.joinToString("") { col ->
			col.toString()
				.groupingBy { it }
				.eachCount()
				.entries
				.sortedWith(comparator)
				.first()
				.key
				.toString()
		}
	}


}