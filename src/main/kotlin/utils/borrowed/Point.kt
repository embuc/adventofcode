package utils.borrowed

import utils.pm

data class Point(var x: Int, var y: Int) {

    val u: Point get() = Point(x, y + 1)
    val r: Point get() = Point(x + 1, y)
    val d: Point get() = Point(x, y - 1)
    val l: Point get() = Point(x - 1, y)

    fun getCardinalNeighbors(): Set<Point> {
        return setOf(u, r, d, l)
    }

    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun rem(other: Point) = Point(x pm other.x, y pm other.y)
    operator fun compareTo(other: Point) = (x + y).compareTo(other.x + other.y)
    enum class Direction(val angle: Int) {
        NORTH(0), EAST(90), SOUTH(180), WEST(270);
        operator fun component1() = this.toPoint().x
        operator fun component2() = this.toPoint().y

        fun toPoint() = when (this) {
            NORTH -> Point(-1, 0)
            EAST -> Point(0, 1)
            SOUTH -> Point(1, 0)
            WEST -> Point(0, -1)
        }
    }
}