package utils

class Line(val point: PointDouble, val velocity: PointDouble, val name:String, time: Double = 1.0) {
	val k: Double
	val n: Double
	private val point2: PointDouble

	init {
		point2 = calculateFuturePosition(point, velocity, time)
		k = (point2.y - point.y) / (point2.x - point.x)
		n = point.y - k * point.x
	}


	private fun calculateFuturePosition(initialPoint: PointDouble, velocity: PointDouble, time: Double): PointDouble {
		return PointDouble(initialPoint.x + velocity.x * time, initialPoint.y + velocity.y * time)
	}

	fun intersectsWith(other: Line): PointDouble? {
		if (k == other.k) {
			return null // Lines are parallel (or coincident)
		}

		val x = (other.n - n) / (k - other.k)
		val y = k * x + n

		return PointDouble(x, y)
	}

	fun getLineEquation(p1: PointDouble, p2: PointDouble): String {
		return "y = ${k}x + $n"
	}
}