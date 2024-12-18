package other

class MinimalBacktrack {
//	Pseudo code for backtracking algorithm
//	fun backtrack(current: SolutionState, allSolutions: MutableList<Solution>) {
//		// 1. Base case: if the solution is complete
//		if (isSolution(current)) {
//			allSolutions.add(current.copy()) // Store the valid solution
//			return
//		}
//
//		// 2. Explore all possible candidates
//		for (candidate in getCandidates(current)) {
//			if (isValid(candidate, current)) {
//				current.add(candidate) // Choose the candidate
//				backtrack(current, allSolutions) // Recurse
//				current.remove(candidate) // Undo the choice (backtrack)
//			}
//		}
//	}

//	Example 1: Generate All Subsets of a Set
//	Problem: Given a list [1, 2, 3], generate all its subsets.
	fun generateSubsets(nums: List<Int>, index: Int = 0, current: MutableList<Int> = mutableListOf()) {
		if (index == nums.size) {
			println(current) // Base case: print the current subset
			return
		}

		// Include nums[index]
		current.add(nums[index])
		generateSubsets(nums, index + 1, current)

		// Exclude nums[index] (backtrack)
		current.removeAt(current.size - 1)
		generateSubsets(nums, index + 1, current)
	}

//	Example 2: Solve the N-Queens Problem
//	Problem: Place N queens on an N x N chessboard such that no two queens attack each other
	fun solveNQueens(N: Int) {
		val board = Array(N) { CharArray(N) { '.' } }

		fun isSafe(row: Int, col: Int): Boolean {
			for (i in 0 until row) {
				if (board[i][col] == 'Q' || // Same column
					col - row + i >= 0 && board[i][col - row + i] == 'Q' || // Left diagonal
					col + row - i < N && board[i][col + row - i] == 'Q') // Right diagonal
					return false
			}
			return true
		}

		fun placeQueens(row: Int) {
			if (row == N) {
				board.forEach { println(it.joinToString("")) }
				println()
				return
			}

			for (col in 0 until N) {
				if (isSafe(row, col)) {
					board[row][col] = 'Q'
					placeQueens(row + 1)
					board[row][col] = '.' // Backtrack
				}
			}
		}

		placeQueens(0)
	}

}

fun main() {
	MinimalBacktrack().generateSubsets(listOf(1, 2, 3))
	MinimalBacktrack().solveNQueens(4) // Example for 4x4 board
}