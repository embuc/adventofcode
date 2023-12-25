package y2023

import Task
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
		println(graph)
		graph.removeAllVertices(minCut)
		return graph.vertexSet().size * minCut.size
	}

	override fun b(): Any {
		TODO("Not yet implemented")
	}
}