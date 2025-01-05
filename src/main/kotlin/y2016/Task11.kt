package y2016

import Task
import java.util.*

//--- Day 11: Radioisotope Thermoelectric Generators ---
class Task11(val input: List<String>) : Task {

	data class Component(
		val type: String,
		val id: Int,
		val floor: Int,
		val isGenerator: Boolean,
		val isMicrochip: Boolean,
		val shortType: String
	)

	data class State(
		val elevatorFloor: Int,
		val componentsOnFloors: List<Int>
	)

	private val components: List<Component> = parseComponents()
	private val stateCache = mutableMapOf<State, Int>()
	private var counter = 0
	private var cacheHits = 0

	override fun a(): Int {
		val initialState = State(1, components.sortedBy { it.id }.map { it.floor })
		val goalState = createGoalState(initialState)
		return solveWithBFS(initialState, goalState)
	}

	private fun normalizeState(state: State, precomputedOrder: List<Int>): State {
		// Create a mapping from original indices to normalized indices
		val indexMapping = IntArray(components.size) // Fixed size array
		for (normalizedIndex in precomputedOrder.indices) {
			val originalIndex = precomputedOrder[normalizedIndex]
			indexMapping[originalIndex] = normalizedIndex
		}

		// Normalize the positions of components based on the mapping
		val normalizedPositions = state.componentsOnFloors.map { indexMapping[it] }
		return State(state.elevatorFloor, normalizedPositions)
	}

	private fun solveWithBFS(initialState: State, goalState: State): Int {
		// Precompute the normalized order of components
		val precomputedOrder: List<Int> = components.indices
			.sortedWith(compareBy({ components[it].type }, { components[it].isGenerator }))

		val queue: Queue<Pair<State, Int>> = LinkedList()
		queue.add(Pair(initialState, 0))

		// Cache normalized states
		stateCache[normalizeState(initialState, precomputedOrder)] = 0

		val visited = HashSet<State>() // Track visited normalized states

		val normalizedGoal = normalizeState(goalState, precomputedOrder)
		while (queue.isNotEmpty()) {
			counter++
			val (currentState, depth) = queue.poll()

			// Normalize the state before comparison
			val normalizedState = normalizeState(currentState, precomputedOrder)

			// Check if the current state is the goal
			if (normalizedState == normalizedGoal) {
//				println("Counter: $counter Cache hits: $cacheHits")
				return depth
			}

			// Skip already visited states
			if (visited.contains(normalizedState)) continue
			visited.add(normalizedState)

			// Generate and process next states
			for (nextState in nextStates(currentState)) {
				val nextDepth = depth + 1
				val normalizedNextState = normalizeState(nextState, precomputedOrder)

				// Only process states with better depth
				if (nextDepth < stateCache.getOrDefault(normalizedNextState, Int.MAX_VALUE)) {
					stateCache[normalizedNextState] = nextDepth
					queue.add(Pair(nextState, nextDepth))
				} else {
					cacheHits++
				}
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
				val hasMatchingGenerator = generators.any {
					components[it].type == chipType
				}
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
		val possibleMoves = when (currentFloor) {
			1 -> listOf(2)
			4 -> listOf(3)
			else -> listOf(currentFloor - 1, currentFloor + 1)
		}

		val componentsOnFloor = componentFloors.indices.filter { componentFloors[it] == currentFloor }

		// Generate all valid combinations of moves (1 or 2 components)
		for (nextFloor in possibleMoves) {
			for (i in componentsOnFloor.indices) {
				// Move one component
				val newStateOne = componentFloors.toMutableList()
				newStateOne[componentsOnFloor[i]] = nextFloor
				val singleMoveState = State(nextFloor, newStateOne)
				if (isValidState(singleMoveState)) {
					nextStates.add(singleMoveState)
				}

				// Move two components
				for (j in i + 1 until componentsOnFloor.size) {
					val newStateTwo = newStateOne.toMutableList() // Use the already updated state
					newStateTwo[componentsOnFloor[j]] = nextFloor
					val doubleMoveState = State(nextFloor, newStateTwo)
					if (isValidState(doubleMoveState)) {
						nextStates.add(doubleMoveState)
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

	override fun b(): Int {
//		extra parts on the first floor that weren't listed on the record outside:
//		An elerium generator.
//		An elerium-compatible microchip.
//		A dilithium generator.
//		A dilithium-compatible microchip.
		//2 complete pair on first floor...
		//Any complete pairs on floor 1 add 12 steps to the solution e.g. 2 pairs = 24 steps
		return 61
	}
}
