package templates
// generic minimal dfs (using graph represented as a map)
fun dfs(graph: Map<String, List<String>>, start: String, visited: MutableSet<String> = mutableSetOf()) {
	visited.add(start) // Mark the current node as visited
	println(start) // Process the current node (e.g., print)

	for (neighbor in graph[start].orEmpty()) { // Iterate through unvisited neighbors
		if (!visited.contains(neighbor)) {
			dfs(graph, neighbor, visited) // Recursive call for unvisited neighbor
		}
	}
}
// generic minimal dfs using simple 2d grid, but use it as template for custom tasks related to conectivity and exploration in grid
fun dfsRecursive(gridWidth: Int, gridHeight: Int, current: Pair<Int, Int>, target: Pair<Int,Int>, visited: MutableSet<Pair<Int, Int>>): Boolean {
	visited.add(current) // set current as visited
	if(current == target) {
		return true // If target found then return true
	}
	for(neighbour in getNeighbors(gridWidth, gridHeight, current)) {
		if (!visited.contains(neighbour)) { // If neighbour is not visited then try DFS on that neighbour
			if(dfsRecursive(gridWidth, gridHeight, neighbour, target, visited)) {
				return true // If neighbour can find the target then return true
			}
		}
	}
	return false // If no valid path found then return false
}

data class Connection(val to: String, val cost: Int)
typealias GraphWithCost = MutableMap<String, MutableSet<Connection>>

// Find all paths from start to target
fun dfsAllPaths(
	graph: GraphWithCost,
	start: String,
	target: String? = null, // Optional target
	currentPath: List<String> = emptyList(),
	currentCost: Int = 0,
	allPaths: MutableList<Pair<List<String>, Int>> = mutableListOf()
) : List<Pair<List<String>,Int>> {

	val newPath = currentPath + start

	if (target == null && newPath.size > 1)
		allPaths.add(newPath to currentCost)

	if (start == target) {
		allPaths.add(newPath to currentCost)
		return allPaths
	}

	for (connection in graph[start].orEmpty()) {
		dfsAllPaths(
			graph,
			connection.to,
			target,
			newPath,
			currentCost + connection.cost,
			allPaths
		)
	}
	return allPaths
}



fun main() {
	val graph: GraphWithCost = mutableMapOf(
		"A" to mutableSetOf(Connection("B", 5), Connection("C", 2)),
		"B" to mutableSetOf(Connection("D", 1)),
		"C" to mutableSetOf(Connection("E", 7), Connection("F", 3)),
		"D" to mutableSetOf(),
		"E" to mutableSetOf(),
		"F" to mutableSetOf()
	)

	// Find all paths from "A" to "F"
	val allPaths1 = dfsAllPaths(graph, "A", "F")
	println("All paths from A to F:")
	allPaths1.forEach { (path, cost) -> println("Path: $path, Cost: $cost") }

	// Find all paths from "A"
	val allPaths2 = dfsAllPaths(graph, "A")
	println("\nAll paths from A:")
	allPaths2.forEach { (path, cost) -> println("Path: $path, Cost: $cost") }
}