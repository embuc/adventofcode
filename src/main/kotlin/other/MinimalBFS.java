package other;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The {@code MinimalBFS} class provides a basic implementation of the Breadth-First Search (BFS) algorithm
 * to find the shortest path in a 2D grid. The grid is represented by a 2D char array,
 * where 'S' denotes the start, 'E' denotes the end, '#' denotes a wall, and '.' denotes a walkable cell.
 */
public class MinimalBFS {

	// Node class to store x and y coordinates and path (via parent)
	static class Node {
		int x;
		int y;
		Node parent;

		Node(int x, int y, Node parent) {
			this.x = x;
			this.y = y;
			this.parent = parent;
		}
	}

	public static int[][] findShortestPath(char[][] grid) {
		int rows = grid.length;
		int cols = grid[0].length;
		int[] start = findChar(grid, 'S');
		int[] end = findChar(grid, 'E');

		// Queue for BFS
		Queue<Node> queue = new LinkedList<>();
		// Adding initial node to the queue
		queue.add(new Node(start[0], start[1], null));
		// Map to check whether a location has already been visited or not
		boolean[][] visited = new boolean[rows][cols];
		visited[start[0]][start[1]] = true;
		Node endNode = null;

		// BFS loop
		while (!queue.isEmpty()) {
			Node current = queue.poll();

			// Goal Check
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
				if (newX >= 0 && newX < rows && newY >= 0 && newY < cols
						&& grid[newX][newY] != '#' && !visited[newX][newY]) {
					// Enqueue neighbor
					queue.add(new Node(newX, newY, current));
					visited[newX][newY] = true;
				}
			}
		}

		// Reconstruct path
		if (endNode == null) {
			return null; // No path found
		}
		// Reconstruct path
		LinkedList<int[]> path = new LinkedList<>();
		Node current = endNode;
		while (current != null) {
			path.addFirst(new int[]{current.x, current.y}); // Add current to front
			current = current.parent; // Get its parent
		}

		// Convert to int[][]
		int[][] shortestPath = new int[path.size()][2];
		for (int i = 0; i < path.size(); i++) {
			shortestPath[i] = path.get(i);
		}

		return shortestPath;
	}

	//Helper method to get coordinates of a char in the grid
	private static int[] findChar(char[][] grid, char target) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == target) {
					return new int[]{i, j};
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
			int[] s = findChar(grid, 'S');
			Node start = new Node(s[0], s[1], null);
			int[] e = findChar(grid, 'E');
			Node end = new Node(e[0], e[1], null);
			printPath(grid, shortestPath, start, end);
//			for(int[] coords : shortestPath) {
//				System.out.println("x: " + coords[0] + ", y: " + coords[1]);
//			}
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