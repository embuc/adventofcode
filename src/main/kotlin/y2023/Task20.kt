package y2023

import Task
import utils.allEquals
import utils.lcm
import utils.readInputAsListOfStrings

// --- Day 20: Pulse Propagation ---
// This one was too hard for me to solve in the given time-frame, this solution is from user: github.com/clouddjr.
// for part one and part two is solution by user: github.com/eagely.
// I'll circle back to this one later (I think this one was hardest this year).
// *** Needs more work ***.
object Task20:Task {

	private val input = readInputAsListOfStrings("~/git/aoc-inputs/2023/2023_20.txt")

	override fun a(): Any {
		return solveA(input)
	}

	override fun b(): Any {
		return solvePart2(input)
	}

	private val destinations = input.map { it.split(" -> ") }.associate { (name, destinations) ->
		name.replace("[%&]".toRegex(), "") to destinations.split(", ")
	}
	private val modules = input.map { Module.from(it.split(" -> ").first()) }.associateBy { it.name } +
			destinations.values.flatten().filter { it !in destinations.keys }.associateWith { Module.Untyped(it) }

	init {
		for ((source, destinations) in destinations) {
			for (destination in destinations) {
				val module = modules.getValue(destination)
				if (module is Module.Conjunction) {
					module.states[source] = false
				}
			}
		}
	}

	fun solveA(input: List<String>): Int {
		var low = 0
		var high = 0

		repeat(1000) {
			var current = listOf(modules.getValue("broadcaster") to false)

			while (current.isNotEmpty()) {
				current = buildList {
					for ((module, receivedPulse) in current) {
						if (receivedPulse) high++ else low++

						val output = module.output(receivedPulse) ?: continue
						for (destination in destinations.getValue(module.name).map { modules.getValue(it) }) {
							destination.receive(module.name, output)
							add(destination to output)
						}
					}
				}
			}
		}

		return low * high
	}

	private sealed class Module {
		abstract val name: String

		open fun receive(source: String, pulse: Boolean) {}

		open fun output(receivedPulse: Boolean): Boolean? = receivedPulse

		data class Broadcaster(override val name: String) : Module()

		data class FlipFlop(override val name: String, var state: Boolean) : Module() {
			override fun receive(source: String, pulse: Boolean) {
				if (!pulse) {
					state = !state
				}
			}

			override fun output(receivedPulse: Boolean) = if (receivedPulse) null else state
		}

		data class Conjunction(override val name: String, val states: MutableMap<String, Boolean>) : Module() {
			override fun receive(source: String, pulse: Boolean) {
				states[source] = pulse
			}

			override fun output(receivedPulse: Boolean) = states.values.all { it }.not()
		}

		data class Untyped(override val name: String) : Module() {
			override fun output(receivedPulse: Boolean) = null
		}

		companion object {
			fun from(str: String): Module {
				return when (str.first()) {
					'%'  -> FlipFlop(name = str.drop(1), state = false)
					'&'  -> Conjunction(name = str.drop(1), states = mutableMapOf())
					else -> Broadcaster(name = str)
				}
			}
		}
	}

	data class Gate(val name: String, var state: Boolean, val targets: List<String>, val memory: HashMap<String, Boolean> = hashMapOf())


	fun solvePart2(input: List<String>): Any {
		val gates = input.map {
			val (name, targets) = it.split(" -> ")
			Gate(name, false, targets.split(", "))
		}
		if (gates.size < 10) return "never (there is no rx)"
		var bonks = 0L
		val queue = ArrayDeque<Pair<String, Gate>>()
		val bread = gates.first { it.name == "broadcaster" }
		gates.filter { it.name.first() == '&' }.forEach { gate -> gate.memory.putAll(gates.filter { gate.name.drop(1) in it.targets }.associate { it.name to false }) }
		val lastconj = gates.first { "rx" in it.targets }.name.drop(1)
		val connectors = gates.filter { it.targets.contains(lastconj) }.map { it.name }
		val cycles = connectors.associate { it.drop(1) to 0L }.toMutableMap()
		while (cycles.any { it.value == 0L }) {
			bonks++
			queue.addAll(bread.targets.map { it to bread })
			while (queue.isNotEmpty()) {
				val (name, origin) = queue.removeFirst()
				if (name == lastconj && origin.state && origin.name in connectors) cycles[origin.name.drop(1)] = bonks
				if (name !in gates.map { it.name.drop(1) }) continue
				val gate = gates.first { it.name.drop(1) == name }
				when (gate.name.first()) {
					'%' -> {
						if (origin.state) continue
						gate.state = !gate.state
					}

					'&' -> {
						gate.memory[origin.name] = origin.state
						gate.state = !gate.memory.values.allEquals(true)
					}
				}
				queue.addAll(gate.targets.map { it to gate })
			}
		}
		return cycles.values.lcm()
	}

}