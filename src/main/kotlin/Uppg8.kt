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

	fun b() {
		val lines = getLinesFromFile("Input8.txt")
		val dict = parseToMap(lines)
		println("steps until all Z: ${countStepsUntilAllZ(dict, steps)}")
	}

	data class ValuePair(val left: String, val right: String)

	fun countStepsUntilAllZ(map: Map<String, ValuePair>, steps: String): Int {
		val startingNodes = map.keys.filter { it.endsWith("A") }
		val currentNodes = startingNodes.toMutableList()
		var stepCount = 0

		// Custom generator for cycling through steps
		fun cycleSteps() = sequence {
			while (true) yieldAll(steps.asIterable())
		}

		val stepsIterator = cycleSteps().iterator()

		while (stepsIterator.hasNext() && !currentNodes.all { it.endsWith("Z") }) {
			val step = stepsIterator.next()
			for (i in currentNodes.indices) {
				map[currentNodes[i]]?.let { pair ->
					currentNodes[i] = if (step == 'L') pair.left else pair.right
				}
			}
			stepCount++
		}

		return if (currentNodes.all { it.endsWith("Z") }) stepCount else -1 // Return -1 if not all end with Z
	}

	fun synchronizedTraverseMap(map: Map<String, ValuePair>, steps: String): List<List<String>> {
		val startingNodes = map.keys.filter { it.endsWith("A") }
		val paths = startingNodes.map { mutableListOf(it) }
		var allEndWithZ = false

		// Custom generator for cycling through steps
		fun cycleSteps() = sequence {
			while (true) yieldAll(steps.asIterable())
		}

		val stepsIterator = cycleSteps().iterator()

		while (stepsIterator.hasNext() && !allEndWithZ) {
			val step = stepsIterator.next()
			paths.forEach { path ->
				val currentKey = path.last()
				map[currentKey]?.let { pair ->
					val nextKey = if (step == 'L') pair.left else pair.right
					path.add(nextKey)
				}
			}

			// Check if all current nodes end with 'Z' after this step
			allEndWithZ = paths.all { it.last().endsWith("Z") }
		}

		return if (allEndWithZ) paths else emptyList()
	}

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