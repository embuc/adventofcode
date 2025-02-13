package templates

/**
 * Computes the shortest paths between all pairs of vertices in a graph using the Floyd-Warshall algorithm.
 *
 * @param vertices: A list of all vertices in the graph.
 * @param edges: A map where the key is a pair of vertices (u, v), and the value is the weight of the edge from u to v.
 * @return A 2D map where distances[u][v] is the shortest distance from vertex u to vertex v.
 */
fun floydWarshall(
	vertices: List<String>,
	edges: Map<Pair<String, String>, Int>
): Map<String, Map<String, Int>> {
	// Step 1: Initialize the distance matrix
	val distances = mutableMapOf<String, MutableMap<String, Int>>()
	for (u in vertices) {
		distances[u] = mutableMapOf()
		for (v in vertices) {
			distances[u]!![v] = when {
				u == v -> 0 // Distance to itself is 0
				else -> edges[u to v] ?: (Int.MAX_VALUE / 2) // Use edge weight or "infinity"
			}
		}
	}

	// Step 2: Relax all pairs of vertices using intermediate vertices
	for (k in vertices) {
		for (i in vertices) {
			for (j in vertices) {
				distances[i]!![j] = minOf(
					distances[i]!![j]!!,
					distances[i]!![k]!! + distances[k]!![j]!!
				)
			}
		}
	}

	return distances
}

// Example usage of the Floyd-Warshall algorithm
fun main() {
	val vertices = listOf("A", "B", "C")
	val edges = mapOf(
		"A" to "B" to 1,
		"B" to "C" to 2,
		"A" to "C" to 4
	)

	val shortestPaths = floydWarshall(vertices, edges)

	// Print the shortest paths
	for (u in vertices) {
		for (v in vertices) {
			println("Shortest path from $u to $v: ${shortestPaths[u]!![v]}")
		}
	}
}