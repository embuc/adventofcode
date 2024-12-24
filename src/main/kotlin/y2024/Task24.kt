package y2024

import Task

//--- Day 24: Crossed Wires ---
class Task24(val input: List<String>) : Task {

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

	data class Circuit(val operation: String, val inputs: Pair<String, String>, val output: String)

	override fun b(): String {

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

		fun process(op: String, op1: Int, op2: Int): Int {
			return when (op) {
				"AND" -> op1 and op2
				"OR" -> op1 or op2
				"XOR" -> op1 xor op2
				else -> op2
			}
		}

		val highestZ = circuits.filter { it.output.startsWith("z") }
			.maxByOrNull { it.output.substring(1).toIntOrNull() ?: 0 }?.output ?: "z00"
		val wrong = mutableSetOf<String>()
		for (circuit in circuits) {
			val (op1, op, op2) = Triple(circuit.inputs.first, circuit.operation, circuit.inputs.second)
			val res = circuit.output
			if (res.startsWith("z") && op != "XOR" && res != highestZ) {
				wrong.add(res)
			}
			if (
				op == "XOR"
				&& !res.startsWith("x") && !res.startsWith("y") && !res.startsWith("z")
				&& !op1.startsWith("x") && !op1.startsWith("y") && !op1.startsWith("z")
				&& !op2.startsWith("x") && !op2.startsWith("y") && !op2.startsWith("z")
			) {
				wrong.add(res)
			}
			if (op == "AND" && "x00" !in listOf(op1, op2)) {
				for (subCircuit in circuits) {
					val (subOp1, subOp, subOp2) = Triple(subCircuit.inputs.first, subCircuit.operation, subCircuit.inputs.second)
					if ((res == subOp1 || res == subOp2) && subOp != "OR") {
						wrong.add(res)
					}
				}
			}
			if (op == "XOR") {
				for (subCircuit in circuits) {
					val (subOp1, subOp, subOp2) = Triple(subCircuit.inputs.first, subCircuit.operation, subCircuit.inputs.second)
					if ((res == subOp1 || res == subOp2) && subOp == "OR") {
						wrong.add(res)
					}
				}
			}
		}

		val operations = circuits.toMutableList()
		while (operations.isNotEmpty()) {
			val circuit = operations.removeAt(0)
			val (op1, op, op2) = Triple(circuit.inputs.first, circuit.operation, circuit.inputs.second)
			if(wire.contains(op1) && wire.contains(op2)){
				val res = circuit.output
				wire[res] = process(op, wire[op1]!!, wire[op2]!!)

			}
			else{
				operations.add(circuit)
			}
		}

		return wrong.sorted().joinToString(",")
	}
}