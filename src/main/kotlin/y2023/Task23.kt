package y2023

import Task
import utils.borrowed.Direction
import utils.borrowed.Point

// --- Day 23: A Long Walk ---
// I had almost a solution for part one, but I kept getting one of the longest paths but not The longest path.
// This solution is from user github.com/eagely. Here for me to study and learn from.
// *** Needs more work ***.
@OptIn(kotlin.ExperimentalStdlibApi::class)
class Task23(private val input: List<String>) : Task {

	private lateinit var grid: Array<Array<Char>>
	private val visited = mutableSetOf<Point>()
	private var max = 0

	override fun a(): Any {
		grid = input.map { it.toCharArray().toTypedArray() }.toTypedArray()
		dfs(Point(0, grid[0].indexOf('.')), Point(grid.size - 1, grid.last().lastIndexOf('.')))
		return max
	}

	private fun getNextPointsA(point: Point): Collection<Point> {
		val cur = grid[point.x][point.y]!!
		return when (cur) {
			'>' -> listOf(point + Direction.EAST.toPoint())
			'v' -> listOf(point + Direction.SOUTH.toPoint())
			else -> {
				point.getCardinalNeighbors()
					.filter { neighbor ->
						neighbor.x in grid.indices && neighbor.y in grid[0].indices
								&& grid[neighbor.x][neighbor.y] != '#'
					}
			}
		}
	}

	private fun getNextPointsB(point: Point): Collection<Point> {
		val cur = grid[point.x][point.y]!!
		return when (cur) {
			'x' -> listOf(point + Direction.EAST.toPoint())
			'V' -> listOf(point + Direction.SOUTH.toPoint())
			else -> {
				point.getCardinalNeighbors()
					.filter { neighbor ->
						neighbor.x in grid.indices && neighbor.y in grid[0].indices
								&& grid[neighbor.x][neighbor.y] != '#'
					}
			}
		}
	}

	override fun b(): Any {
		grid = input.map { it.toCharArray().toTypedArray() }.toTypedArray()
		val start = Point(0, grid[0].indexOf('.'))
		val end = Point(grid.size - 1, grid.last().indexOf('.'))
		return dfsOptimized(makeAdjacencies(grid), start, end, mutableMapOf())!!
	}

	private fun makeAdjacencies(grid: Array<Array<Char>>): Map<Point, Map<Point, Int>> {
		val adjacencies = grid.indices.flatMap { x ->
			grid[0].indices.mapNotNull { y ->
				if (grid[x][y] != '#') {
					Point(x, y) to Direction.entries.mapNotNull { (dx, dy) ->
						val nx = x + dx
						val ny = y + dy
						if (nx in grid.indices && ny in grid[0].indices && grid[nx][ny] != '#') Point(nx, ny) to 1 else null
					}.toMap(mutableMapOf())
				} else null
			}
		}.toMap(mutableMapOf())

		adjacencies.keys.toList().forEach { key ->
			adjacencies[key]?.takeIf { it.size == 2 }?.let { neighbors ->
				val left = neighbors.keys.first()
				val right = neighbors.keys.last()
				val totalSteps = neighbors[left]!! + neighbors[right]!!
				adjacencies.getOrPut(left) { mutableMapOf() }.merge(right, totalSteps, ::maxOf)
				adjacencies.getOrPut(right) { mutableMapOf() }.merge(left, totalSteps, ::maxOf)
				listOf(left, right).forEach { adjacencies[it]?.remove(key) }
				adjacencies.remove(key)
			}
		}
		return adjacencies
	}

	private fun dfsOptimized(graph: Map<Point, Map<Point, Int>>, cur: Point, end: Point, seen: MutableMap<Point, Int>): Int? {
		if (cur == end) {
			return seen.values.sum()
		}

		var best: Int? = null
		(graph[cur] ?: emptyMap()).forEach { (neighbor, steps) ->
			if (neighbor !in seen) {
				seen[neighbor] = steps
				val res = dfsOptimized(graph, neighbor, end, seen)
				if (best == null || (res != null && res > best!!)) {
					best = res
				}
				seen.remove(neighbor)
			}
		}
		return best
	}

	private fun dfs(cur: Point, end: Point, steps: Int = 0) {
		if (cur == end) {
			max = maxOf(max, steps)
			return
		}
		visited.add(cur)
		getNextPointsA(cur).filter { it !in visited }.forEach { dfs(it, end, steps + 1) }
		visited.remove(cur)
	}

}