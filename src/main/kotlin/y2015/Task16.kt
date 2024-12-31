package y2015

import Task

//--- Day 16: Aunt Sue ---
class Task16(val input: List<String>): Task {

	private val message = """
		children: 3
		cats: 7
		samoyeds: 2
		pomeranians: 3
		akitas: 0
		vizslas: 0
		goldfish: 5
		trees: 3
		cars: 2
		perfumes: 1
	""".trim().lines().map { it.trim() }

	override fun a(): Any {
		val dict = mutableMapOf<String, Int>()
		for (line in message) {
			val parts = line.split(":")
			val key = parts[0].trim()
			val value = parts[1].trim().toInt()
			dict[key] = value
		}
		for (line in input) {
			val split = line.indexOfFirst { it==':' }
			val sue = line.substring(0,split).trim().removePrefix("Sue ").toInt()
			val properties = line.substring(split + 1).trim().split(",")
			var match = true
			for (property in properties) {
				val parts = property.split(":")
				val key = parts[0].trim()
				val value = parts[1].trim().toInt()
				if (dict[key] != value) {
					match = false
					break
				}
			}
			if (match) {
				return sue
			}
		}

		return -1
	}

	override fun b(): Any {
		TODO("Not yet implemented")
	}

}