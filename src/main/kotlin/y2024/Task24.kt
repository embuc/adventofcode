package y2024

import Task

//--- Day 24: Crossed Wires ---
class Task24(val input: List<String>) : Task {

	data class Circuit(val operation: String, val inputs: Pair<String, String>, val output: String)

	override fun a(): Long {
		val (wire, circuits) = parseInput(input)
		calculateWireValues(wire, circuits)
		return convertWireToDecimal(wire).toLong()
	}

	override fun b(): String {
		val (wire, circuits) = parseInput(input)
		val highestZ = findHighestZ(circuits)
		val invalidOutputs = findInvalidOutputs(circuits, highestZ)
		calculateWireValues(wire, circuits)
		return invalidOutputs.sorted().joinToString(",")
	}

	private fun parseInput(input: List<String>): Pair<MutableMap<String, Int>, MutableList<Circuit>> {
		val wire = mutableMapOf<String, Int>()
		val circuits = mutableListOf<Circuit>()
		val (wireLines, circuitLines) = input.partition { it.contains(":") }

		wireLines.forEach { line ->
			val (key, value) = line.split(":").map { it.trim() }
			wire[key] = value.toInt()
		}

		circuitLines.filter { it.isNotBlank() }.forEach { line ->
			val (left, output) = line.split("->").map { it.trim() }
			val parts = left.split(" ")
			circuits.add(Circuit(parts[1], Pair(parts[0], parts[2]), output))
		}

		return Pair(wire, circuits)
	}

	private fun processOperation(op: String, op1: Int, op2: Int): Int = when (op) {
		"AND" -> op1 and op2
		"OR" -> op1 or op2
		"XOR" -> op1 xor op2
		else -> op2
	}

	private fun calculateWireValues(wire: MutableMap<String, Int>, circuits: List<Circuit>) {
		val processedCircuits = mutableSetOf<Circuit>()

		while (processedCircuits.size < circuits.size) {
			circuits.forEach { circuit ->
				if (circuit in processedCircuits) return@forEach

				val (input1, input2) = circuit.inputs
				val op1Value = wire[input1] ?: return@forEach
				val op2Value = wire[input2] ?: return@forEach

				val result = processOperation(circuit.operation, op1Value, op2Value)
				wire[circuit.output] = result
				processedCircuits.add(circuit)
			}
		}
	}

	private fun findHighestZ(circuits: List<Circuit>): String {
		return circuits.filter { it.output.startsWith("z") }
			.maxByOrNull { it.output.substring(1).toIntOrNull() ?: 0 }
			?.output ?: "z00"
	}

	private fun findInvalidOutputs(circuits: List<Circuit>, highestZ: String): Set<String> {
		val invalidOutputs = mutableSetOf<String>()

		circuits.forEach { circuit ->
			val (input1, input2) = circuit.inputs
			val output = circuit.output

			// Conditions for invalid outputs
			if (output.startsWith("z") && circuit.operation != "XOR" && output != highestZ) {
				invalidOutputs.add(output)
			}

			if (circuit.operation == "XOR" && isNonXYZ(input1, input2, output)) {
				invalidOutputs.add(output)
			}

			if (circuit.operation == "AND" && "x00" !in listOf(input1, input2)) {
				addInvalidAndOutputs(circuit, circuits, invalidOutputs)
			}

			if (circuit.operation == "XOR") {
				addInvalidXorOutputs(circuit, circuits, invalidOutputs)
			}
		}

		return invalidOutputs
	}

	private fun isNonXYZ(op1: String, op2: String, output: String): Boolean {
		return listOf(op1, op2, output).all { !it.startsWith("x") && !it.startsWith("y") && !it.startsWith("z") }
	}

	private fun addInvalidAndOutputs(
		circuit: Circuit,
		circuits: List<Circuit>,
		invalidOutputs: MutableSet<String>
	) {
		circuits.forEach { subCircuit ->
			val (subInput1, subInput2) = subCircuit.inputs
			if ((circuit.output == subInput1 || circuit.output == subInput2) && subCircuit.operation != "OR") {
				invalidOutputs.add(circuit.output)
			}
		}
	}

	private fun addInvalidXorOutputs(
		circuit: Circuit,
		circuits: List<Circuit>,
		invalidOutputs: MutableSet<String>
	) {
		circuits.forEach { subCircuit ->
			val (subInput1, subInput2) = subCircuit.inputs
			if ((circuit.output == subInput1 || circuit.output == subInput2) && subCircuit.operation == "OR") {
				invalidOutputs.add(circuit.output)
			}
		}
	}

	private fun convertWireToDecimal(wire: Map<String, Int>): String {
		return wire.keys
			.filter { it.startsWith("z") }
			.sortedDescending().joinToString("") { wire[it].toString() }
			.toLong(2)
			.toString()
	}
}