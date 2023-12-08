const val steps = "LRLRRRLRRLRRRLRRRLLLLLRRRLRLRRLRLRLRRLRRLRRRLRLRLRRLLRLRRLRRLRRLRRRLLRRRLRRRLRRLRLLLRRLRRRLRLRRLRRRLRRLRLLLRRRLRRLRRLRRRLRRRLRRRLRLRLRLRRRLRRRLLLRRLLRRRLRLRLRRRLRRRLRRLRRRLRLRLLRRRLRLRRLRLRLRRLLLRRRLRRRLRRLRRLRLRRLLRRLRRRLRRRLLRRRLRRLRLLRRLRLRRLLRRRLLLLRRLRRRLRLRRLLRLLRRRLLRRLLRRRLRRRLRRLLRLRLLRRLLRLLLRRRR"

class Uppg8 {

	fun a() {
		val lines = getLinesFromFile("Input8.txt")
		val dict = parseToMap(lines)
//		println(dict)
		val path = traverseMap(dict, "AAA", steps)
		println(path)
		println(path.size)
	}

	data class ValuePair(val left: String, val right: String)

	fun parseToMap(strings: List<String>): Map<String, ValuePair> {
		return strings.mapNotNull { str ->
			val parts = str.split("=").map { it.trim() }
			if (parts.size == 2) {
				val key = parts[0]
				val valuePart = parts[1].removeSurrounding("(", ")")
				val values = valuePart.split(",").map { it.trim() }
				if (values.size == 2) key to ValuePair(values[0], values[1]) else null
			} else {
				null
			}
		}.toMap()
	}

	fun traverseMap(map: Map<String, ValuePair>, startKey: String, steps: String): List<String> {
		var currentKey = startKey
		val path = mutableListOf<String>()
		println("START: currentKey: $currentKey")

		// Custom generator for cycling through steps
		fun cycleSteps() = sequence {
			while (true) yieldAll(steps.asIterable())
		}

		val stepsIterator = cycleSteps().iterator()
		var i = 0
		while (stepsIterator.hasNext()) {
			i++
			path.add(currentKey)
			val step = stepsIterator.next()
			val pair = map[currentKey] ?: break // Break if the current key is not found
//			println("step: $step, pair: $pair")

			currentKey = if (step == 'L') pair.left else pair.right
//			println("currentKey: $currentKey")
			if (currentKey == "ZZZ") break
		}

		return path
	}

}