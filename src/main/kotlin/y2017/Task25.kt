package y2017

import Task

//--- Day 25: The Halting Problem ---
class Task25 : Task {

	enum class State {
		A, B, C, D, E, F
	}

	override fun a(): Any {
		val tape = mutableMapOf<Int, Int>()
		var cursor = 0
		var state = State.A
		var newState:Pair<State, Int>
		repeat(12586542) {
			when (state) {
				State.A -> {
					newState = stateA(cursor, tape)
				}
				State.B -> {
					newState = stateB(cursor, tape)
				}
				State.C -> {
					newState = stateC(cursor, tape)
				}
				State.D -> {
					newState = stateD(cursor, tape)
				}
				State.E -> {
					newState = stateE(cursor, tape)
				}
				State.F -> {
					newState = stateF(cursor, tape)
				}
			}
			state = newState.first
			cursor = newState.second

		}
		return tape.count { it.value == 1}
	}

	fun stateA(cursor: Int, tape: MutableMap<Int, Int>): Pair<State, Int> {
		var newCursor = cursor
		if (tape.getOrDefault(cursor, 0) == 0) {
			tape[cursor] = 1
			newCursor++
		} else {
			tape[cursor] = 0
			newCursor--
		}
		return State.B to newCursor
	}

	fun stateB(cursor: Int, tape: MutableMap<Int, Int>): Pair<State, Int> {
		var newCursor = cursor
		if (tape.getOrDefault(cursor, 0) == 0) {
			tape[cursor] = 0
			newCursor++
			return State.C to newCursor
		}
		tape[cursor] = 1
		newCursor--
		return State.B to newCursor
	}

	fun stateC(cursor: Int, tape: MutableMap<Int, Int>): Pair<State, Int> {
		var newCursor = cursor
		if (tape.getOrDefault(cursor, 0) == 0) {
			tape[cursor] = 1
			newCursor++
			return State.D to newCursor
		}
		tape[cursor] = 0
		newCursor--
		return State.A to newCursor
	}

	fun stateD(cursor: Int, tape: MutableMap<Int, Int>): Pair<State, Int> {
		var newCursor = cursor
		if (tape.getOrDefault(cursor, 0) == 0) {
			tape[cursor] = 1
			newCursor--
			return State.E to newCursor
		}
		tape[cursor] = 1
		newCursor--
		return State.F to newCursor
	}

	fun stateE(cursor: Int, tape: MutableMap<Int, Int>): Pair<State, Int> {
		var newCursor = cursor
		if (tape.getOrDefault(cursor, 0) == 0) {
			tape[cursor] = 1
			newCursor--
			return State.A to newCursor
		}
		tape[cursor] = 0
		newCursor--
		return State.D to newCursor
	}

	fun stateF(cursor: Int, tape: MutableMap<Int, Int>): Pair<State, Int> {
		var newCursor = cursor
		if (tape.getOrDefault(cursor, 0) == 0) {
			tape[cursor] = 1
			newCursor++
			return State.A to newCursor
		}
		tape[cursor] = 1
		newCursor--
		return State.E to newCursor
	}

	override fun b(): Any {
		return "Merry Christmas!"
	}
}