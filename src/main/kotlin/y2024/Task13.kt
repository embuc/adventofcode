package y2024

import Task
import utils.solveLinearEquations2x2

/*--- Day 13: Claw Contraption ---*/
class Task13(val input: List<String>) : Task {

	data class Equation(val a: Double, val b: Double, val c: Double)

	override fun a(): Long {
		// cost: 3 tokens to push a 1 token to push b
		var cost = 0L
		for (i in input.indices step 4) {
			val (eq1, eq2) = getEquations(input.subList(i, i + 3))
			val (a1, b1, c1) = eq1
			val (a2, b2, c2) = eq2
			// Solve the system of equations
			solveLinearEquations2x2(a1, b1, c1, a2, b2, c2)?.let { (x, y) ->
				cost += x * 3 + y
			}
		}
		return cost
	}

	override fun b(): Long {
		val const = 10000000000000L
		var cost = 0L
		for (i in input.indices step 4) {
			val (eq1, eq2) = getEquations(input.subList(i, i + 3))
			val eq11 = eq1.copy(c = eq1.c + const)
			val eq22 = eq2.copy(c = eq2.c + const)
			solveLinearEquations2x2(eq11.a, eq11.b, eq11.c, eq22.a, eq22.b, eq22.c)?.let { (x, y) ->
				cost += x * 3 + y
			}
		}
		return cost
	}

	private fun getEquations(subList: List<String>): Pair<Equation, Equation> {
		val (a1, a2) = subList[0].split(":")[1].split(", ").map { it.split("+")[1].toDouble() }
		val (b1, b2) = subList[1].split(":")[1].split(", ").map { it.split("+")[1].toDouble() }
		val (c1, c2) = subList[2].split(":")[1].split(", ").map { it.split("=")[1].toDouble() }
		return Equation(a1, b1, c1) to Equation(a2, b2, c2)
	}

}