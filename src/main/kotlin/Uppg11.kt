import kotlin.random.Random

class Uppg11 {

	fun a() {
		val lines = getLinesFromFile("Input11.txt")
		val grid = expandGrid(lines)
		val galaxyPairs = findAllGalaxyPairs(grid)
		val shortestPaths = galaxyPairs.map { bfs(it.first, it.second, grid) }
		val sumOfPaths = shortestPaths.sumOf { it.size - 1 }
		println(sumOfPaths)
	}

	fun b() {
		val lines = getLinesFromFile("Input11.txt")
		val grid = expandGrid(lines)
		val galaxyPairs = findAllGalaxyPairs(grid)
		val shortestPaths = galaxyPairs.map { bfs(it.first, it.second, grid) }

//		val totalLength = shortestPaths.sumOf { innerList ->
//			innerList.sumOf { tile ->
//				if (tile.times > 1) tile.times * 1_000_000 else 1
//			}
//		}

		val totalLength = shortestPaths.sumOf { innerList ->
			val innerSum = innerList.sumOf { tile ->
				if (tile.times > 1) tile.times * 1_000_000 else 1
			}

			// Adjust the sum of each inner list by -1
			if (innerSum > 0) innerSum - 1 else 0
		}

		println("Total length: $totalLength")
//		val sumOfPaths = shortestPaths.sumOf { it.size - 1 }
//		println(sumOfPaths)
	}

	fun printGrid(grid: List<List<Tile>>) {
		for (row in grid) {
			for (tile in row) {
				print("$tile ")
			}
			println()
		}
	}

	fun printGridCoord(grid: List<List<Tile>>) {
		for (row in grid) {
			for (tile in row) {
				print("(${tile.x},${tile.y}) ")
			}
			println()
		}
	}

	fun printExpandedGridIds(grid: List<List<Tile>>) {
		for (row in grid) {
			for (tile in row) {
				print(" ${tile.times} ")
			}
			println()
		}
	}

	fun expandGrid(lines: List<String>): List<List<Tile>> {
		val colCount = lines.first().length
		val rowCount = lines.size

		val grid = List(rowCount) { r -> List(colCount) { c -> Tile(
			r,
			c,
			lines[r][c],
			".",
			0
		) } }
		val fullRows = BooleanArray(rowCount) { true }
		val fullCols = BooleanArray(colCount) { true }

		// Labeling galaxies
		var galaxyCounter = 1
		for (r in grid.indices) {
			for (c in grid[0].indices) {
				if (grid[r][c].isGalaxy()) {
					grid[r][c].label = galaxyCounter++.toString()
				}
			}
		}
		println("Galaxy count: ${galaxyCounter - 1}")

		// Check for full rows and columns
		for (r in 0 until rowCount) {
			for (c in 0 until colCount) {
				if (grid[r][c].isGalaxy()) {
					fullRows[r] = false
					fullCols[c] = false
				}
			}
		}

		// Expand rows
		val expandedRows = mutableListOf<List<Tile>>()
		var i = 0

		grid.forEachIndexed { index, row ->
			row.forEach { tile -> tile.x += i }
			expandedRows.add(row)
			if (fullRows[index]) {
				println("Adding row at index $index")
				i++
				val newRow = List(colCount) { c ->
					Tile(index + i, c, '.', ".", 1)
				}
				expandedRows.add(newRow)
			}
		}

		// Expand columns
		val expandedGrid = expandedRows.map { row ->
			val newRow = mutableListOf<Tile>()
			i = 0;
			row.forEachIndexed { index, tile ->
				tile.y += i
				newRow.add(tile)
				if (fullCols[index]) {
					i++
					newRow.add(Tile(tile.x, tile.y + 1, '.', ".", tile.times + 1))
				}
			}
			newRow.toTypedArray().toList()
		}

		return expandedGrid
	}

	fun findAllGalaxyPairs(grid: List<List<Tile>>): List<Pair<Tile, Tile>> {
		val galaxies = grid.flatten().filter { it.isGalaxy() }
		val pairs = mutableListOf<Pair<Tile, Tile>>()

		for (i in galaxies.indices) {
			for (j in i + 1 until galaxies.size) {
				pairs.add(Pair(galaxies[i], galaxies[j]))
			}
		}

		return pairs
	}

	fun bfs(start: Tile, end: Tile, grid: List<List<Tile>>): List<Tile> {
		val visited = mutableSetOf<Tile>()
		val queue = ArrayDeque<List<Tile>>()
		queue.add(listOf(start))

		while (queue.isNotEmpty()) {
			val path = queue.removeFirst()
			val node = path.last()

			if (node == end) {
				return path
			}

			if (node in visited) {
				continue
			}

			visited.add(node)

			val neighbors = getNeighbors(node, grid)
			for (neighbor in neighbors) {
				if (neighbor !in visited) {
					val newPath = path.toMutableList()
					newPath.add(neighbor)
					queue.add(newPath)
				}
			}
		}

		return emptyList()
	}

	fun getNeighbors(tile: Tile, grid: List<List<Tile>>): List<Tile> {
		val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
		val neighbors = mutableListOf<Tile>()

		for ((dx, dy) in directions) {
			val newX = tile.x + dx
			val newY = tile.y + dy
			if (newX in grid.indices && newY in grid[newX].indices) {
				neighbors.add(grid[newX][newY])
			}
		}

		return neighbors
	}
}

class Tile(var x: Int, var y: Int, private val value: Char, var label: String, val times: Int = 0) {

	fun isGalaxy(): Boolean {
		return value == '#'
	}
	override fun toString(): String {
		return if (isGalaxy()) label.toString() else value.toString()
	}
}
