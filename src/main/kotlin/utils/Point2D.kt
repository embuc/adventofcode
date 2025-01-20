package utils

import kotlin.math.abs

data class Point2D(var x: Int = 0, var y: Int = 0, var direction: Direction = Direction.NORTH) {
	private val up: Point2D get() = Point2D(x - 1, y)
	private val right: Point2D get() = Point2D(x, y + 1)
	private val down: Point2D get() = Point2D(x + 1, y)
	private val left: Point2D get() = Point2D(x, y - 1)

	private val upRight: Point2D get() = Point2D(x - 1, y + 1)
	private val downRight: Point2D get() = Point2D(x + 1, y + 1)
	private val downLeft: Point2D get() = Point2D(x + 1, y - 1)
	private val upLeft: Point2D get() = Point2D(x - 1, y - 1)

	fun getCardinalNeighbors(): Set<Point2D> {
		return setOf(up, right, down, left)
	}

	fun getAllNeighbors(): Set<Point2D> {
		return setOf(up, right, down, left, upRight, downRight, downLeft, upLeft)
	}

	fun getRelativeDirection(point: Point2D): Direction {
		return when (point) {
			up -> Direction.NORTH
			right -> Direction.EAST
			down -> Direction.SOUTH
			left -> Direction.WEST
			else -> throw IllegalArgumentException("Point $point is not a neighbor of $this")
		}
	}

	//get fun for relative directions considering current direction
	fun getRelativeLeftNeighbor(): Point2D {
		return when (direction) {
			Direction.NORTH -> left
			Direction.EAST -> up
			Direction.SOUTH -> right
			Direction.WEST -> down
		}
	}

	fun turn(turn: Turn) {
		direction = when (turn) {
			Turn.LEFT -> Direction.entries[(direction.ordinal + 3) % 4]
			Turn.RIGHT -> Direction.entries[(direction.ordinal + 1) % 4]
			Turn.AROUND -> Direction.entries[(direction.ordinal + 2) % 4]
		}
	}

	fun face(direction: Direction) {
		this.direction = direction
	}

	enum class Turn {
		LEFT, RIGHT, AROUND
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
	fun toPair(): Pair<Int, Int> {
		return Pair(this.x, this.y)
	}

	fun isInside(verticalSize: Int, horizontalSize: Int): Boolean {
		return x in 0 until verticalSize && y in 0 until horizontalSize
	}
 	//get where we came from one tile before
	fun getPreviousOpositeDirection(): Point2D {
		return when (direction) {
			Direction.NORTH -> down
			Direction.EAST -> left
			Direction.SOUTH -> up
			Direction.WEST -> right
		}
	}

	enum class Direction(val angle: Int) {
		NORTH(0), EAST(90), SOUTH(180), WEST(270);

		operator fun component1() = this.toPoint2D().x
		operator fun component2() = this.toPoint2D().y

		private fun toPoint2D() = when (this) {
			NORTH -> Point2D(-1, 0)
			EAST -> Point2D(0, 1)
			SOUTH -> Point2D(1, 0)
			WEST -> Point2D(0, -1)
		}
	}
}
