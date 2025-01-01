package y2015

import Task

//--- Day 24: It Hangs in the Balance ---
class Task24(val input: List<String>) : Task {

	override fun a(): Long {
		val packages = input.map { it.toInt() }
		return solveA(packages)
	}

	override fun b(): Long {
		val packages = input.map { it.toInt() }
		return solveB(packages)
	}

	private fun solveA(packages: List<Int>): Long {
		return solve(packages, 3)
	}

	private fun solveB(packages: List<Int>): Long {
		return solve(packages, 4)
	}

	private fun solve(packages: List<Int>, numGroups: Int): Long {
		val totalWeight = packages.sum()
		val targetWeight = totalWeight / numGroups
		//increase chance of better pruning by sorting in descending order
		val sortedPackages = packages.sortedDescending()

		var minGroup1Size = Int.MAX_VALUE
		var minQe = Double.POSITIVE_INFINITY

		fun findGroup1(
			index: Int,
			currentGroup: MutableList<Int>,
			currentSum: Int,
			remainingPackages: MutableList<Int>
		) {

			// Prune if current group is too large or sum exceeds target
			if (currentGroup.size >= minGroup1Size || currentSum > targetWeight) return

			if (currentSum == targetWeight) {

				if (canSplitRemaining(remainingPackages, targetWeight, numGroups)) {
					minGroup1Size = currentGroup.size
					minQe = currentGroup.fold(1.0) { acc, num -> acc * num }
				}
				return
			}

			if (index >= sortedPackages.size) return

			// Try including current package
			val pkg = sortedPackages[index]
			currentGroup.add(pkg)
			remainingPackages.remove(pkg)
			findGroup1(index + 1, currentGroup, currentSum + pkg, remainingPackages)
			currentGroup.removeAt(currentGroup.size - 1)
			remainingPackages.add(pkg)

			// Try excluding current package
			findGroup1(index + 1, currentGroup, currentSum, remainingPackages)
		}


		findGroup1(0, mutableListOf(), 0, sortedPackages.toMutableList())

		return if (minQe == Double.POSITIVE_INFINITY) -1L else minQe.toLong()
	}

	private fun canSplitRemaining(remaining: List<Int>, target: Int, numGroups: Int): Boolean {
		if (numGroups == 3 && remaining.sum() != target * 2) return false
		if (numGroups == 4 && remaining.sum() != target * 3) return false

		fun canMakeSum(index: Int, currentSum: Int, target: Int): Boolean {
			if (currentSum == target) return true
			if (currentSum > target || index >= remaining.size) return false

			// Try including current package
			if (canMakeSum(index + 1, currentSum + remaining[index], target)) return true
			// Try excluding current package
			return canMakeSum(index + 1, currentSum, target)
		}

		return canMakeSum(0, 0, target)
	}
}