package utils

import kotlin.math.abs

data class Point2D(var x: Int = 0, var y: Int = 0, var direction: Direction = Direction.NORTH) {
	val up: Point2D get() = Point2D(x - 1, y)
	val right: Point2D get() = Point2D(x, y + 1)
	val down: Point2D get() = Point2D(x + 1, y)
	val left: Point2D get() = Point2D(x, y - 1)

	fun getCardinalNeighbors(): Set<Point2D> {
		return setOf(up, right, down, left)
	}

//	fun getAllNeighbors(): Set<Point2D> {
//		return setOf(up, upright, right, downright, down, downleft, left, upleft)
//	}

	fun turn(turn: Turn) {
		direction = when (turn) {
			Turn.LEFT -> Direction.values()[(direction.ordinal + 3) % 4]
			Turn.RIGHT -> Direction.values()[(direction.ordinal + 1) % 4]
		}
	}
	fun face(direction: Direction) {
		this.direction = direction
	}

	enum class Turn {
		LEFT, RIGHT
	}

	fun move(steps: Int) {
		when (direction) {
			Direction.NORTH -> x -= steps
			Direction.EAST -> y += steps
			Direction.SOUTH -> x += steps
			Direction.WEST -> y -= steps
		}
	}

	fun manhattanDistance(other: Point2D) = abs(x - other.x) + abs(y - other.y)
	fun toXY(): Point2D {
		return Point2D(this.x, this.y)
	}

	fun isInside(verticalSize: Int, horizontalSize: Int): Boolean {
		return x in 0 until verticalSize && y in 0 until horizontalSize
	}

	enum class Direction(val angle: Int) {
		NORTH(0), EAST(90), SOUTH(180), WEST(270);

		operator fun component1() = this.toPoint2D().x
		operator fun component2() = this.toPoint2D().y

		fun toPoint2D() = when (this) {
			NORTH -> Point2D(-1, 0)
			EAST -> Point2D(0, 1)
			SOUTH -> Point2D(1, 0)
			WEST -> Point2D(0, -1)
		}
	}
}
