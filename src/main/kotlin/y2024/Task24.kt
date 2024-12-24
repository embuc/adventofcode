package y2024

import Task

//--- Day 24: Crossed Wires ---
class Task24(val input: List<String>) : Task {

	data class Circuit(val operation: String, val inputs: Pair<String, String>, val output: String)
	override fun a(): Long {
		val wire = mutableMapOf<String, Int>()
		val circuits = mutableListOf<Circuit>()
		val (wireLines, circuitLines) = input.partition { it.contains(":") }
			.let { it -> Pair(it.first, it.second.filter { it.isNotBlank() }) }
		for (line in wireLines) {
			val parts = line.split(":")
			wire[parts[0].trim()] = parts[1].trim().toInt()
		}
		for (line in circuitLines) {
			val parts = line.split("->")
			val circuit = parts[0].trim().split(" ")
			circuits.add(Circuit(circuit[1].trim(), Pair(circuit[0].trim(), circuit[2].trim()), parts[1].trim()))
		}
		//let loop through circuits and calculate values
		while (true) {
			var processed = mutableSetOf<Circuit>()
			for (circuit in circuits) {
				if (processed.contains(circuit)) {
					continue
				}
				val (a, b) = circuit.inputs
				val aValue = wire[a] ?: continue
				val bValue = wire[b] ?: continue
				processed.add(circuit)
				val output = circuit.output
				val result = when (circuit.operation) {
					"AND" -> aValue and bValue
					"OR" -> aValue or bValue
					"XOR" -> aValue xor bValue
					else -> bValue
				}
				wire[output] = result

			}
			if (processed.size == circuits.size) {
				break
			}
		}
		// construct result by taking all z values where z00 is largest significant bit
		val result = wire.filterKeys { it.startsWith("z") }
			.keys.sorted()
			.fold(0L) { acc, key ->
				val bitValue = wire[key]?.toLong() ?: 0L
				val index = key.substring(1).toIntOrNull() ?: 0
				acc + (bitValue shl index)
			}


		return result
	}

	override fun b(): String {
		return ""
	}
}