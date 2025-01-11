package templates;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import utils.ConsoleColors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * The {@code MinimalBFS} class provides a basic implementation of the Breadth-First Search (BFS) algorithm to find the shortest path in a 2D grid.
 * The grid is represented by a 2D char array, where 'S' denotes the start, 'E' denotes the end, '#' denotes a wall, and '.' denotes a walkable cell.
 */
public class MinimalBFS {
//  Pseudocode:
//	function BFS(startNode, targetNode):
//	create a queue
//	enqueue startNode into the queue
//	mark startNode as visited
//
//    while queue is not empty:
//	currentNode = dequeue from the queue
//
//        if currentNode == targetNode:
//			return "Target Found"  // or return the node, path, etc.
//
//			for each neighbor in neighbors of currentNode:
//			if neighbor is not visited:
//	mark neighbor as visited
//	enqueue neighbor into the queue
//
//    return "Target Not Found"  // or appropriate failure message

	// Node class to store x and y coordinates and path (via parent)
	@EqualsAndHashCode
	@AllArgsConstructor
	public static class Node {
		public int x;
		public int y;
		public Node parent;
		public int depth;
		public String name;

		public Node(int x, int y) {
			this(x, y, null, 0, null);
		}

		public Node(int x, int y, String name) {
			this(x, y, null, 0, name);
		}

		public Node(int x, int y, Node parent) {
			this(x, y, parent, 0, null);
		}

		public Node(int x, int y, int depth) {
			this(x, y, null, depth, null);
		}

		public String toString() {
			return "Node{" +
					"x=" + x +
					", y=" + y +
					'}';
		}
	}

	/*
	 * Find the shortest path in a 2D grid. Path includes S and E. '#' denotes a wall, '.' denotes a walkable cell.
	 */
	public static int[][] findShortestPath(@NotNull char[][] it, Node from, Node to) {
		return findShortestPath(it, new int[]{from.x, from.y}, new int[]{to.x, to.y});
	}

	public static int[][] findShortestPath(@NotNull char[][] grid) {
		int[] start = findChar(grid, 'S');
		int[] end = findChar(grid, 'E');
		return findShortestPath(grid, start, end);
	}

	public static int[][] findShortestPath(char[][] gridIncoming, int []start, int[] end) {
		char[][] grid = new char[gridIncoming.length][gridIncoming[0].length];
		System.arraycopy(gridIncoming, 0, grid, 0, gridIncoming.length);
		int rows = grid.length;
		int cols = grid[0].length;

		// Queue for BFS
		Queue<Node> queue = new LinkedList<>();
		// Adding initial node to the queue
		queue.add(new Node(start[0], start[1]));
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
	public static int[] findChar(char[][] grid, char target) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == target) {
					return new int[]{i, j};
				}
			}
		}
		return null; // Target not found
	}

	public static Set<Node> findReachableWithinSteps(int startX, int startY, int stepsLimit, char[][] grid) {
		Set<Node> visited = new HashSet<>();
		Queue<Node> queue = new LinkedList<>();

		queue.add(new Node(startX, startY, 0));
		visited.add(new Node(startX, startY));
		Set<Node> reachablePoints = new HashSet<>();

		int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right


		while (!queue.isEmpty()) {
			Node currentPoint = queue.poll();
			int x = currentPoint.x;
			int y = currentPoint.y;
			int depth = currentPoint.depth;
			reachablePoints.add(new Node(x, y));
			// If our depth is too high, we don't visit neighbours
			if (depth >= stepsLimit) {
				continue;
			}


			// Check valid neighbour
			for (int[] dir : directions) {
				int newX = x + dir[0];
				int newY = y + dir[1];

				if (isValid(newX, newY, grid)) {
					Node newPoint = new Node(newX, newY);
					if (visited.add(newPoint)) {
						queue.add(new Node(newX, newY, depth + 1));
					}

				}
			}
		}
		return reachablePoints;
	}

	private static boolean isValid(int x, int y, char[][] grid) {
		return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] != '#';
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
			printPath(grid, shortestPath);
		} else {
			System.out.println("No path found.");
		}
	}

	public static void printPath(char[][] grid, int[][] shortestPath) {
		List<int[][]> paths = new ArrayList<>();
		paths.add(shortestPath);
		int[] s = findChar(grid, 'S');
		Node start = new Node(s[0], s[1], "S");
		int[] e = findChar(grid, 'E');
		Node stop = new Node(e[0], e[1], "E");
		List<Node> startEndNodes = new ArrayList<>();
		startEndNodes.add(start);
		startEndNodes.add(stop);
		printPath(grid, paths, startEndNodes);
	}

	public static void printPath(char[][] gridIncoming, List<int[][]> shortestPaths, List<Node> startEndNodes) {
		char[][] grid = new char[gridIncoming.length][gridIncoming[0].length];
		System.arraycopy(gridIncoming, 0, grid, 0, gridIncoming.length);
		System.out.println("Printing Shortest Path(s). Grid size: x:" + grid.length + " y:" + grid[0].length);

		for (int[][] shortestPath : shortestPaths) {
			for (int[] coords : shortestPath) {
				grid[coords[0]][coords[1]] = '*';
			}
		}
		for (Node node : startEndNodes) {
			grid[node.x][node.y] = node.name.charAt(0);
		}

		for (char[] chars : grid) {
			for (char aChar : chars) {
				if (aChar == '#') {
					ConsoleColors.printGray("#");
				} else if (aChar == '.') {
					ConsoleColors.printLightGray(".");
				} else if (aChar == '*') {
					ConsoleColors.printGreen("*");
				} else {
					//start/end
					ConsoleColors.printMagenta("*");
				}
			}
			System.out.println();
		}
	}
}