package y2016

import Task
import java.awt.Component
import java.util.*

//--- Day 11: Radioisotope Thermoelectric Generators ---
class Task11(val input: List<String>) : Task {

	private data class Component(
		val type: String,
		val id: Int,
		val floor: Int,
		val isGenerator: Boolean,
		val isMicrochip: Boolean,
		val shortType: String
	)

	private val components: List<Component> = parseComponents()

	private data class State(
		val elevatorFloor: Int,
		val componentsOnFloors: List<Int>
	) {
		fun normalize(components: List<Component>): State {
			// Group components by type and create normalized ordering (pairs are equivalent by type!)
			val componentsByType = components.groupBy { it.type }
			val normalizedComponents = componentsByType.values
				.sortedBy { it.first().type }
				.flatMap { it.sortedBy { c -> c.isGenerator } }

			val positionMapping = components.indices.zip(normalizedComponents.indices).toMap()

			val newPositions = componentsOnFloors.indices.map {
				componentsOnFloors[positionMapping[it] ?: it]
			}

			return State(elevatorFloor, newPositions)
		}
	}

	override fun a(): Int {
		val initialState = State(1, components.sortedBy { it.id }.map { it.floor })
		val goalState = createGoalState(initialState)
		return solveWithBFS(initialState, goalState)
	}

	private fun solveWithBFS(initialState: State, goalState: State): Int {
		val queue: Queue<Pair<State, Int>> = LinkedList()
		queue.add(Pair(initialState, 0))
		val visited = mutableSetOf<State>()
		var count = 0

		while (queue.isNotEmpty()) {
			count++
			val (currentState, depth) = queue.poll()

			// Normalize the state before comparison
			val normalizedState = currentState.normalize(components)
			val normalizedGoal = goalState.normalize(components)

			if (normalizedState == normalizedGoal) return depth
			if (visited.contains(normalizedState)) continue

			visited.add(normalizedState)

			for (nextState in nextStates(currentState)) {
				queue.add(Pair(nextState, depth + 1))
			}
		}
		return -1
	}

	private fun isValidState(state: State): Boolean {
		val componentFloors = state.componentsOnFloors

		for (floor in 1..4) {
			val microchips = components.indices.filter {
				components[it].isMicrochip && componentFloors[it] == floor
			}
			val generators = components.indices.filter {
				components[it].isGenerator && componentFloors[it] == floor
			}

			// no generators on this floor - microchips are safe
			if (generators.isEmpty()) continue

			for (chipIndex in microchips) {
				val chipType = components[chipIndex].type
				//  matching generator?
				val hasMatchingGenerator = generators.any {
					components[it].type == chipType
				}
				// If there are generators present but no matching one, the chip is fried
				if (!hasMatchingGenerator) return false
			}
		}
		return true
	}

	private fun createGoalState(initialState: State): State {
		val maxFloor = 4
		return State(maxFloor, initialState.componentsOnFloors.map { maxFloor })
	}

	private fun nextStates(state: State): List<State> {
		val nextStates = mutableListOf<State>()
		val currentFloor = state.elevatorFloor
		val componentFloors = state.componentsOnFloors

		// Possible elevator moves
		val possibleMoves = mutableListOf<Int>()
		if (currentFloor < 4) possibleMoves.add(currentFloor + 1)
		if (currentFloor > 1) possibleMoves.add(currentFloor - 1)

		val componentsOnFloor = componentFloors.indices.filter {
			componentFloors[it] == currentFloor
		}

		// Generate states for each move + component carrying combo
		for (nextFloor in possibleMoves) {
			// Try moving one component
			for (i in componentsOnFloor.indices) {
				val component1 = componentsOnFloor[i]
				val newState = componentFloors.toMutableList()
				newState[component1] = nextFloor

				val newStateObj = State(nextFloor, newState)
				if (isValidState(newStateObj)) {
					nextStates.add(newStateObj)
				}

				// Try moving two components
				for (j in i + 1 until componentsOnFloor.size) {
					val component2 = componentsOnFloor[j]
					val newStateWithTwo = componentFloors.toMutableList()
					newStateWithTwo[component1] = nextFloor
					newStateWithTwo[component2] = nextFloor

					val newStateTwoObj = State(nextFloor, newStateWithTwo)
					if (isValidState(newStateTwoObj)) {
						nextStates.add(newStateTwoObj)
					}
				}
			}
		}
		return nextStates
	}

	private fun parseComponents(): List<Component> {
		val components = mutableListOf<Component>()
		var id = 1
		for ((index, line) in input.withIndex()) {
			if (index == 3) continue
			val parts = line.replace("and ", "").split(" ")
			val floor = index + 1
			val componentsParts = parts.drop(4).chunked(3)
			for (component in componentsParts) {
				val type = component[1].substringBefore("-")
				val isGenerator = component[2].replace(".", "").replace(",", "") == "generator"
				val isMicrochip = component[2].replace(".", "").replace(",", "") == "microchip"
				val shortType = type.substring(0, 1).uppercase(Locale.getDefault()) +
						component[2].substring(0, 1).uppercase(Locale.getDefault())
				components.add(Component(type, id++, floor, isGenerator, isMicrochip, shortType))
			}
		}
		return components
	}
	private fun printComponents(components: List<Component>, elevator: Int) {
		val componentsColSorted = components.sortedBy { it.id }
		val compOnFloor = mutableMapOf<Int, List<Component>>()
		compOnFloor.putIfAbsent(4, componentsColSorted.filter { it.floor == 4 })
		compOnFloor.putIfAbsent(3, componentsColSorted.filter { it.floor == 3 })
		compOnFloor.putIfAbsent(2, componentsColSorted.filter { it.floor == 2 })
		compOnFloor.putIfAbsent(1, componentsColSorted.filter { it.floor == 1 })

		val maxFloorLength = components.size

		for (floor in 4 downTo 1) {
			print("F$floor ")
			if(elevator == floor) print(" E  ")
			else print(" .  ")
			val floorCompMap = compOnFloor[floor]!!.associateBy { it.id }
			for (ix in 1..maxFloorLength) {
				if(floorCompMap.containsKey(ix)) {
					val comp = floorCompMap[ix]
					print(" ${comp?.shortType} ")
				} else {
					print(" .  ")
				}
			}
			println()
		}
	}

	override fun b(): Int {
		return 0
	}
}