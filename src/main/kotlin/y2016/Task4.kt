package y2016

import Task

//--- Day 4: Security Through Obscurity ---
class Task4(val input:List<String>) : Task {

	override fun a(): Any {
		var sum = 0
		for (line in input ) {
			val letters = line.substringBeforeLast("-").replace("-", "")
			val secondPart = line.substringAfterLast("-")
			val sectorId = secondPart.substringBefore("[").toInt()
			val checksum = secondPart.substringAfter("[").substringBefore("]")
			if(realRoom(letters, checksum)) {
				sum += sectorId
			}
		}
		return sum
	}

	private fun realRoom(letters: String, checksum: String): Boolean {
		val chars = letters.toCharArray().toList()
		val counts = mutableMapOf<Char, Int>()
		chars.toList().groupingBy { it }.eachCountTo(counts)
		val sortedChars = chars.sortedWith(compareBy({ -counts[it]!! }, { it })).distinct().subList(0,5).joinToString("")
		return sortedChars == checksum
	}

	override fun b(): Any {
		for (line in input ) {
			val letters = line.substringBeforeLast("-")
			val secondPart = line.substringAfterLast("-")
			val sectorId = secondPart.substringBefore("[").toInt()
			val decrypted = letters.map {
				if(it == '-') ' '
				else {
					val shift = sectorId % 26
					val newChar = it + shift
					if(newChar > 'z') newChar - 26 else newChar
				}
			}.joinToString("")
			if(decrypted.contains("north")) {
				return sectorId
			}
		}
		return -1
	}

}