package other

class MinimalDP {
	/*
	General Pseudo Code for Dynamic Programming Algorithms
	plaintext
	Copy code
	1. Identify the problem as suitable for DP (check for overlapping subproblems and optimal substructure).
	2. Define the DP array/table to store the results of subproblems.
	3. Initialize the base cases in the DP table.
	4. Determine the direction of filling the DP table (e.g., bottom-up or top-down).
	5. Fill the DP table:
	- For each entry in the DP table, compute its value based on previously solved subproblems.
	- Use the relationship/formula derived from the problem's optimal substructure.
	6. Use the values stored in the DP table to construct the solution to the original problem.
	7. Return the final answer from the DP table (usually the last entry or the best among entries).
	8. If necessary, reconstruct the solution based on the DP table.
	 */

	fun fibonacci(n: Int): Int {
		// Check if the base condition is met (Step 1 & 3)
		if (n <= 1) return n

		// Define the DP table (Step 2)
		val dp = IntArray(n + 1)

		// Initialize base cases (Step 3)
		dp[0] = 0  // F(0)
		dp[1] = 1  // F(1)

		// Fill the DP table from bottom up (Step 4 & 5)
		for (i in 2..n) {
			dp[i] = dp[i - 1] + dp[i - 2]  // Fibonacci relation (Step 5)
		}

		// Return the final answer from the DP table (Step 7)
		return dp[n]
	}

	fun coinChange(coins: IntArray, amount: Int): Int {
		// Define a large value for initialization (beyond the problem scope) (Step 2)
		val max = amount + 1

		// Define the DP table and initialize with a large number (Step 2 & 3)
		val dp = IntArray(max) { max }
		dp[0] = 0  // No coins needed to make amount 0 (Step 3)

		// Fill the DP table from bottom up (Step 4 & 5)
		for (i in 1..amount) {
			for (coin in coins) {
				if (coin <= i) {
					// Update dp[i] by taking the minimum value (Step 5)
					dp[i] = minOf(dp[i], dp[i - coin] + 1)
				}
			}
		}

		// Check the value of dp[amount] and return the appropriate result (Step 7)
		return if (dp[amount] > amount) -1 else dp[amount]  // If no solution is found, return -1
	}


	companion object {
		fun fibonacci(n: Int): Int {
			if (n <= 1) return n
			val dp = IntArray(n + 1)
			dp[0] = 0
			dp[1] = 1
			for (i in 2..n) {
				dp[i] = dp[i - 1] + dp[i - 2]
			}
			return dp[n]
		}

		// Function to find the minimum number of coins for a given amount
		fun coinChange(coins: IntArray, amount: Int): Int {
			val max = amount + 1
			val dp = IntArray(max) { max }
			dp[0] = 0
			for (i in 1..amount) {
				for (coin in coins) {
					if (coin <= i) {
						dp[i] = minOf(dp[i], dp[i - coin] + 1)
					}
				}
			}
			return if (dp[amount] > amount) -1 else dp[amount]
		}

		@JvmStatic
		fun main(args: Array<String>) {
			val fibNumber = 10
			println("Fibonacci of $fibNumber is: ${fibonacci(fibNumber)}")

			val coins = intArrayOf(1, 2, 5)
			val amount = 11
			println("Minimum coins to make $amount with ${coins.joinToString(", ")}: ${coinChange(coins, amount)}")
		}
	}
}