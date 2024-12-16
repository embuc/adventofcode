package other;


import java.util.*;

/**
 * The {@code MinimalDijkstra} class provides a basic implementation of Dijkstra's
 * algorithm to find the shortest path in a 2D grid, considering a uniform cost
 * for each move. The grid is represented by a 2D char array, where 'S' denotes
 * the start, 'E' denotes the end, '#' denotes a wall, and '.' denotes a
 * walkable cell.
 */
public class MinimalDijkstra {

	// Node class to store x, y coordinates, cost, and parent
	static class Node {
		int x;
		int y;
		int cost;
		Node parent;

		Node(int x, int y, int cost, Node parent) {
			this.x = x;
			this.y = y;
			this.cost = cost;
			this.parent = parent;
		}
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Node node = (Node) o;
			return x == node.x && y == node.y;
		}
	}

	public static int[][] findShortestPath(char[][] grid) {
		int rows = grid.length;
		int cols = grid[0].length;
		int[] start = findChar(grid, 'S');
		int[] end = findChar(grid, 'E');

		// Priority Queue for Dijkstra's, ordered by cost
		PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost));
		//Adding start node with cost 0
		queue.add(new Node(start[0], start[1], 0, null));
		//Map of visited locations with their cost
		Map<Node, Integer> visited = new HashMap<>();
		visited.put(new Node(start[0], start[1], 0, null), 0);

		Node endNode = null;

		// Dijkstra's loop
		while (!queue.isEmpty()) {
			Node current = queue.poll();

			// Goal check
			if (current.x == end[0] && current.y == end[1]) {
				endNode = current; // We have reached our end
				break;
			}

			// Explore neighbors (up, down, left, right)
			int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
			for (int[] direction : directions) {
				int newX = current.x + direction[0];
				int newY = current.y + direction[1];

				// Check for valid positions and not a wall
				if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && grid[newX][newY] != '#') {
					int newCost = current.cost + 1; // Assuming all moves cost 1 for minimal code

					Node neighbor = new Node(newX, newY, newCost, current);
					// Only explore the node if the cost is better than what we had before
					if (!visited.containsKey(neighbor) || visited.get(new Node(newX, newY, 0, null)) > newCost) {
						queue.add(neighbor);
						visited.put(new Node(newX, newY, 0, null), newCost);
					}
				}
			}
		}
		// Reconstruct path
		if (endNode == null) {
			return null; // No path found
		}
		LinkedList<int[]> path = new LinkedList<>();
		Node current = endNode;
		while (current != null) {
			path.addFirst(new int[] {current.x, current.y}); // Add current to front
			current = current.parent; // Get its parent
		}

		// Convert to int[][]
		int[][] shortestPath = new int[path.size()][2];
		for(int i = 0; i < path.size(); i++){
			shortestPath[i] = path.get(i);
		}

		return shortestPath;
	}
	//Helper method to get coordinates of a char in the grid
	private static int[] findChar(char[][] grid, char target) {
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				if(grid[i][j] == target) {
					return new int[] {i,j};
				}
			}
		}
		return null; // Target not found
	}

	public static void main(String[] args) {
		char[][] grid = {
				{'S', '.', '#', '.', '.'},
				{'.', '.', '#', '.', '.'},
				{'.', '.', '.', '#', '.'},
				{'.', '#', '.', '.', '#'},
				{'.', '#', '.', '#', '.'},
				{'.', '#', '.', '.', 'E'}
		};


		int[][] shortestPath = findShortestPath(grid);
		if (shortestPath != null) {
			System.out.println("Shortest Path:");
			int [] s = findChar(grid, 'S');
			Node start = new Node(s[0], s[1], 0, null);
			int [] e = findChar(grid, 'E');
			Node end = new Node(e[0], e[1], 0, null);
			printPath(grid, shortestPath, start, end);
		} else {
			System.out.println("No path found.");
		}
	}

	private static void printPath(char[][] grid, int[][] shortestPath, Node start, Node stop) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (i == start.x && j == start.y) {
					System.out.print("S ");
				} else if (i == stop.x && j == stop.y) {
					System.out.print("E ");
				} else {
					boolean isPath = false;
					for (int[] coords : shortestPath) {
						if (coords[0] == i && coords[1] == j) {
							isPath = true;
							break;
						}
					}
					if (isPath) {
						System.out.print("* ");
					} else {
						System.out.print(grid[i][j] + " ");
					}
				}
			}
			System.out.println();
		}
	}
}