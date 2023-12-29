package utils.borrowed

data class Point2D(val x: Int, val y: Int) {
	fun neighbours(): List<Point2D> {
		return listOf(
			Point2D(x - 1, y),
			Point2D(x + 1, y),
			Point2D(x, y - 1),
			Point2D(x, y + 1),
		)
	}

	operator fun plus(other: Point2D): Point2D = Point2D(x + other.x, y + other.y)

	companion object {
		val NORTH = Point2D(0, -1)
		val EAST = Point2D(1, 0)
		val SOUTH = Point2D(0, 1)
		val WEST = Point2D(-1, 0)
	}
}