package y2024

import Task

//--- Day 24: Crossed Wires ---
class Task24(val input: List<String>) : Task {

	data class Circuit(val operation: String, val inputs: Pair<String, String>, val output: String)

	override fun a(): Long {
		val (wire, circuits) = parseInput(input)
		calculateValues(wire,circuits)
		return toDecimalFromZ(wire).toLong()
	}

	override fun b(): String {
		val (wire, circuits) = parseInput(input)
		val highestZ = findHighestZ(circuits)

		val wrongOutputs = mutableSetOf<String>()
		for (circuit in circuits) {
			val (op1, op, op2) = Triple(circuit.inputs.first, circuit.operation, circuit.inputs.second)
			val res = circuit.output
			if (res.startsWith("z") && op != "XOR" && res != highestZ) {
				wrongOutputs.add(res)
			}
			if (
				op == "XOR"
				&& !res.startsWith("x") && !res.startsWith("y") && !res.startsWith("z")
				&& !op1.startsWith("x") && !op1.startsWith("y") && !op1.startsWith("z")
				&& !op2.startsWith("x") && !op2.startsWith("y") && !op2.startsWith("z")
			) {
				wrongOutputs.add(res)
			}
			if (op == "AND" && "x00" !in listOf(op1, op2)) {
				for (subCircuit in circuits) {
					val (subOp1, subOp, subOp2) = Triple(subCircuit.inputs.first, subCircuit.operation, subCircuit.inputs.second)
					if ((res == subOp1 || res == subOp2) && subOp != "OR") {
						wrongOutputs.add(res)
					}
				}
			}
			if (op == "XOR") {
				for (subCircuit in circuits) {
					val (subOp1, subOp, subOp2) = Triple(subCircuit.inputs.first, subCircuit.operation, subCircuit.inputs.second)
					if ((res == subOp1 || res == subOp2) && subOp == "OR") {
						wrongOutputs.add(res)
					}
				}
			}
		}

		calculateValues(wire,circuits)

		println(wrongOutputs.sorted().joinToString(","))
		return wrongOutputs.sorted().joinToString(",")
	}

	fun parseInput(input: List<String>): Pair<MutableMap<String, Int>, MutableList<Circuit>> {
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
		return Pair(wire, circuits)
	}

	fun process(op: String, op1: Int, op2: Int): Int {
		return when (op) {
			"AND" -> op1 and op2
			"OR" -> op1 or op2
			"XOR" -> op1 xor op2
			else -> op2
		}
	}

	fun calculateValues(
		wire: MutableMap<String, Int>,
		circuits: MutableList<Circuit>
	) {
		while (true) {
			var processed = mutableSetOf<Circuit>()
			for (circuit in circuits) {
				if (processed.contains(circuit)) {
					continue
				}
				val (op1, op2) = circuit.inputs
				val op1Value = wire[op1] ?: continue
				val op2Value = wire[op2] ?: continue
				processed.add(circuit)
				val res = circuit.output
				val result = process(circuit.operation, op1Value, op2Value)
				wire[res] = result
			}
			if (processed.size == circuits.size) {
				break
			}
		}
	}

	fun findHighestZ(circuits: List<Circuit>): String {
		return  circuits.filter { it.output.startsWith("z") }
			.maxByOrNull { it.output.substring(1).toIntOrNull() ?: 0 }?.output ?: "z00"
	}

	fun toDecimalFromZ(wire: Map<String, Int>):String {
		val bits = wire.keys.filter { it.startsWith("z") }.sortedDescending().map { wire[it].toString() }
		return bits.joinToString("").toLong(2).toString()
	}


}