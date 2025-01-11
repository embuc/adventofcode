package y2016

import utils.readInputAsListOfStrings
import java.util.*

data class Move(val pos: Pair<Int, Int>, val visited: List<Pair<Int, Int>>, val depth: Int)

fun Move.adjacentCells(maze: List<String>): List<Pair<Int, Int>> {
	return listOf(Pair(pos.first - 1, pos.second),
		Pair(pos.first + 1, pos.second),
		Pair(pos.first, pos.second + 1),
		Pair(pos.first, pos.second - 1))
		.filter { it.first >= 0 && it.second >= 0 }
		.filterNot { it in visited }
		.filterNot { maze.isWall(it) }
}

fun walkMaze(maze: List<String>, start: Pair<Int, Int>, digits: Set<Int>): Map<Int, Int> {
	var queue: Queue<Move> = ArrayDeque<Move>()
	queue.add(Move(Pair(start.first, start.second), listOf(start), 0))
	val remaining = digits.toMutableSet()
	val distances = mutableMapOf<Int, Int>()
	val visited = mutableSetOf<Pair<Int, Int>>()
	while (queue.isNotEmpty() && remaining.isNotEmpty()) {
		val move = queue.remove()!!
		if (move.pos in visited) {
			continue
		}
		visited.add(move.pos)
		if (maze.charAt(move.pos).isDigit()) {
			val d = maze.charAt(move.pos).toString().toInt()
			if (d in remaining) {
				remaining.remove(d)
				distances.put(d, move.depth)
			}
		}
		move.adjacentCells(maze).map { Move(it, move.visited + it, move.depth + 1) }.forEach { queue.add(it) }
	}
	return distances
}

// Given a collection of distances, find minimum route between the points in any order
fun findMinimumRoute(digits: Set<Int>, distances: Map<Int, Map<Int, Int>>, andReturn: Boolean = false): Int {
	tailrec fun inner(from: Int, remaining: Set<Int>, acc: Int): Int {
		return when {
			remaining.size == 0 -> acc + if ( andReturn) distances[from]!![0]!! else 0
			else -> remaining.map { inner(it, remaining.minus(it), acc + distances[from]!![it]!!) }.min()
		}
	}
	return inner(0, digits.minus(0), 0)
}

fun List<String>.isWall(pos: Pair<Int, Int>) = this.isWall(pos.first, pos.second)
fun List<String>.isWall(x: Int, y: Int) = this.charAt(x, y) == '#'
fun List<String>.charAt(pos: Pair<Int, Int>) = this.charAt(pos.first, pos.second)
fun List<String>.charAt(x: Int, y: Int) = this[y][x]

fun List<String>.getDigitLocations(): Map<Int, Pair<Int, Int>> {
	val digits = (0 until this.size)
		.map { y ->
			(0 until this[y].length)
				.filter { this[y][it].isDigit() }
				.map { this[y][it].toString().toInt() to Pair(it, y) }
		}.flatten().toMap()
	return digits
}

fun List<String>.getDistanceMatrix(): Map<Int, Map<Int, Int>> {
	val digitLocations = this.getDigitLocations()
	return digitLocations.keys.map { Pair(it, walkMaze(this, digitLocations[it]!!, digitLocations.keys)) }.toMap()
}

fun main(args: Array<String>) {
	val maze: List<String> = readInputAsListOfStrings("~/git/aoc-inputs/2016/2016_24.txt")
	val distances = maze.getDistanceMatrix()
	println(distances)
	println("Part 1: " + findMinimumRoute(distances.keys, distances))
	println("Part 2: " + findMinimumRoute(distances.keys, distances, andReturn=true))
}