// @author embuc
package utils

fun <T> Collection<T>.allEquals(value: T): Boolean {
	for (i in this) if (i != value) return false
	return true
}

fun <T> Collection<T>.forEachPair(action: (T, T) -> Unit) {
	val list = this.toList()
	for (i in list.indices) {
		for (j in i + 1 until list.size) {
			action(list[i], list[j])
		}
	}
}

//positive modulus of an integer.
infix fun Int.pm(other: Int): Int {
	val mod = this % other
	return if (mod < 0) mod + other else mod
}

fun Collection<Long>.lcm() = reduce { a, b -> lcm(a, b) }

fun lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)

fun gcd(a: Long, b: Long): Long = if (a == 0L) b else gcd(b % a, a)

fun IntRange.size() = last - start + 1

fun die(): Nothing = throw RuntimeException("boom crash bang")

operator fun <T> List<T>.component6(): T {
	return this[5]
}

fun solveLinearEquations2x2(
	a1: Double, b1: Double, c1: Double,
	a2: Double, b2: Double, c2: Double
): Pair<Long, Long>? {
	// Calculate the determinant of the coefficient matrix
	val det = a1 * b2 - a2 * b1

	// If the determinant is zero, the system has no unique solution
	if (det == 0.0) return null

	val detX = c1 * b2 - c2 * b1
	val detY = a1 * c2 - a2 * c1

	val x = detX / det
	val y = detY / det
	// Check if x and y are positive integers
	if (x > 0 && y > 0 && x % 1 == 0.0 && y % 1 == 0.0) {
		return x.toLong() to y.toLong()
	}

	// If x or y is not a positive integer, return no solution
	return null
}


