package y2023

import Task
import org.graphstream.graph.implementations.SingleGraph
import org.jgrapht.alg.StoerWagnerMinimumCut
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.DefaultUndirectedGraph
import utils.Utils.split

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
		System.setProperty("org.graphstream.ui", "swing")
//		println(graph)
		println("vertices: " + graph.vertexSet().size)
		println("edges:" + graph.edgeSet().size)
		utils.Graphs.visualizeGraph(graph)
		Thread.sleep(300000)
		graph.removeAllVertices(minCut)
		return graph.vertexSet().size * minCut.size
	}

	override fun b(): Any {
		return "Red button pressed!"
	}

}

fun main() {
	System.setProperty("org.graphstream.ui", "swing")

	val graph = SingleGraph("Example")
	graph.setStrict(false);
	graph.setAutoCreate( true );
	graph.addNode("A")
	graph.addNode("B")
	graph.addEdge("AB", "A", "B")

	graph.display()
}
