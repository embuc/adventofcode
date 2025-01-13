package y2017

import Task

//--- Day 6: Memory Reallocation ---
class Task6(val input: String) : Task {

	override fun a(): Any {
		val memoryBanks = parseInput()
		val seenStates = HashSet<List<Int>>()

		while (true) {
			val currentState = memoryBanks.toList()
			if (currentState in seenStates) {
				return seenStates.size
			}

			seenStates.add(currentState)
			redistributeBlocks(memoryBanks)
		}
	}

	override fun b(): Any {
		val memoryBanks = parseInput()
		val seenStates = HashSet<List<Int>>()
		val statesInLoop = HashSet<List<Int>>()
		var isSecondPhase = false

		while (true) {
			val currentState = memoryBanks.toList()

			if (currentState in statesInLoop) {
				return statesInLoop.size
			}

			if (currentState in seenStates) {
				isSecondPhase = true
				seenStates.clear()
				statesInLoop.addAll(seenStates)
			}

			if (isSecondPhase) {
				statesInLoop.add(currentState)
			} else {
				seenStates.add(currentState)
			}

			redistributeBlocks(memoryBanks)
		}
	}

	private fun parseInput(): IntArray {
		return input.split("\t").map { it.toInt() }.toIntArray()
	}

	private fun redistributeBlocks(banks: IntArray) {
		val maxIndex = banks.withIndex().maxBy { it.value }.index
		val blocksToRedistribute = banks[maxIndex]

		banks[maxIndex] = 0

		var currentIndex = maxIndex
		repeat(blocksToRedistribute) {
			currentIndex = (currentIndex + 1) % banks.size
			banks[currentIndex]++
		}
	}
}