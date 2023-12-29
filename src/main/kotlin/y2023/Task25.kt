package y2023

import Task
import org.jgrapht.alg.StoerWagnerMinimumCut
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.DefaultUndirectedGraph

// --- Day 25: Snowverload ---
// I read about the problem on reddit, and went with jgrapt. I used GraphStream to visualize the graph.
// Basically I could just visualize the graph and see the answer. I did not know about the StoerWagnerMinimumCut
// algorithm before, so I used that too just to check it out.
// *** Needs more work ***.
class Task25(private val input:List<String>): Task {

	override fun a(): Any {
		val graph = DefaultUndirectedGraph<String, DefaultEdge>(DefaultEdge::class.java)
		input.forEach {
			val nodes = it.split(":")
			val root = nodes.first()
			graph.addVertex(root)
			for (i in nodes.last().split()) {
				graph.addVertex(i)
				graph.addEdge(root, i)
			}
		}
		val minCut = StoerWagnerMinimumCut(graph).minCut()
//		System.setProperty("org.graphstream.ui", "swing")
//		println(graph)
//		println("vertices: " + graph.vertexSet().size)
//		println("edges:" + graph.edgeSet().size)
//		utils.Graphs.visualizeGraph(graph)
//		Thread.sleep(300000)
		graph.removeAllVertices(minCut)
		return graph.vertexSet().size * minCut.size
	}

	override fun b(): Any {
		return "Red button pressed!"
	}

	fun String.split() = this.split(" ").dropBlanks()
	fun <T> Collection<T>.dropBlanks() = this.filter { it.toString().isNotBlank() }
}

//fun main() {
//	System.setProperty("org.graphstream.ui", "swing")
//
//	val graph = SingleGraph("Example")
//	graph.setStrict(false);
//	graph.setAutoCreate( true );
//	graph.addNode("A")
//	graph.addNode("B")
//	graph.addEdge("AB", "A", "B")
//
//	graph.display()
//}
