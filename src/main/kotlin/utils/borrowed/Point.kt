package utils.borrowed

import utils.pm
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sign

data class Point(var x: Int, var y: Int) {
    constructor(x: Number, y: Number) : this(x.toInt(), y.toInt())

    val u: Point get() = Point(x, y + 1)
    val ur: Point get() = Point(x + 1, y + 1)
    val r: Point get() = Point(x + 1, y)
    val dr: Point get() = Point(x + 1, y - 1)
    val d: Point get() = Point(x, y - 1)
    val dl: Point get() = Point(x - 1, y - 1)
    val l: Point get() = Point(x - 1, y)
    val ul: Point get() = Point(x - 1, y + 1)
    val sign: Point get() = Point(x.sign, y.sign)

    fun isInside(maxX: Int, maxY: Int): Boolean {
        return x in 0 until maxX && y in 0 until maxY
    }

    fun getCardinalNeighbors(): Set<Point> {
        return setOf(u, r, d, l)
    }

    fun getNeighbors(): Set<Point> {
        return setOf(u, ur, r, dr, d, dl, l, ul)
    }

    fun lineTo(other: Point): List<Point> {
        val xDelta = (other.x - x).sign
        val yDelta = (other.y - y).sign
        return generateSequence(this) { last ->
            Point(last.x + xDelta, last.y + yDelta).takeIf { it != other }
        }.toList()
    }

    fun rotate(degrees: Int = 180): Point {
        return when (degrees.absoluteValue % 360) {
            0 -> Point(x, y)
            90 -> Point(-y, x)
            180 -> Point(-x, -y)
            270 -> Point(y, -x)
            else -> throw IllegalArgumentException("Rotation must be a multiple of 90 degrees")
        }
    }

    fun invert() = Point(y, x)

    fun getCloserOrEqualPoints(target: Point): Set<Point> =
        (x - this.manhattanDistance(target) .. x + this.manhattanDistance(target)).flatMap { dx ->
            (y - this.manhattanDistance(target) .. y + this.manhattanDistance(target)).mapNotNull { dy ->
                Point(dx, dy).takeIf { it.manhattanDistance(this) <= this.manhattanDistance(target) }
            }
        }.toSet()

    fun mod(value: Int) = Point(this.x % value, this.y % value)

    fun getCloserPoints(target: Point): Set<Point> =
        (x - this.manhattanDistance(target) .. x + this.manhattanDistance(target)).flatMap { dx ->
            (y - this.manhattanDistance(target) .. y + this.manhattanDistance(target)).mapNotNull { dy ->
                Point(dx, dy).takeIf { it.manhattanDistance(this) < this.manhattanDistance(target) }
            }
        }.toSet()

    fun toDirection(): Direction {
        return when {
            x == 0 && y == 1 -> Direction.NORTH
            x == 0 && y == -1 -> Direction.SOUTH
            x == 1 && y == 0 -> Direction.EAST
            x == -1 && y == 0 -> Direction.WEST
            else -> throw IllegalArgumentException("Point $this is not a direction")
        }
    }
    fun toDirectionOnGrid(): Direction {
        return when {
            x == -1 && y == 0 -> Direction.NORTH
            x == 1 && y == 0 -> Direction.SOUTH
            x == 0 && y == 1 -> Direction.EAST
            x == 0 && y == -1 -> Direction.WEST
            else -> throw IllegalArgumentException("Point $this is not a direction")
        }
    }
    infix fun pm(other: Point) = this % other
    fun manhattanDistance(other: Point) = abs(x - other.x) + abs(y - other.y)
    fun gridPlus(other: Direction) = this + other.toPoint()
    fun gridMinus(other: Direction) = this - other.toPoint()
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun plus(other: Direction) = this + other.toPoint()
    operator fun unaryPlus() = Point(+x, +y)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    operator fun minus(other: Direction) = this - other.toPoint()
    operator fun unaryMinus() = Point(-x, -y)
    operator fun times(other: Point) = Point(x * other.x, y * other.y)
    operator fun div(other: Point) = Point(x / other.x, y / other.y)
    operator fun rem(other: Point) = Point(x pm other.x, y pm other.y)
    operator fun rem(other: Int) = Point(x pm other, y pm other)
    operator fun inc() = Point(x + 1, y + 1)
    operator fun dec() = Point(x - 1, y - 1)
    operator fun compareTo(other: Point) = (x + y).compareTo(other.x + other.y)
    override fun toString() = "$x-$y"


    companion object {
        val ORIGIN = Point(0, 0)
        fun of(input: String): Point {
            val (x, y) = input.split(',', '-').map { it.trim().toInt() }
            return Point(x, y)
        }
    }
}