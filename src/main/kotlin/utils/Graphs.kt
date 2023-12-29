// @author embuc
package utils

import org.graphstream.graph.implementations.SingleGraph
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge

object Graphs {

	fun visualizeGraph(jGraphTGraph: Graph<String, DefaultEdge>) {
		System.setProperty("org.graphstream.ui", "swing")

		val graphStream = SingleGraph("Graph Visualization")

		System.setProperty("org.graphstream.ui", "swing") // Use Swing for the UI

		for (vertex in jGraphTGraph.vertexSet()) {
			graphStream.addNode(vertex).setAttribute("ui.label", vertex)
		}

		for (edge in jGraphTGraph.edgeSet()) {
			val source = jGraphTGraph.getEdgeSource(edge)
			val target = jGraphTGraph.getEdgeTarget(edge)
			graphStream.addEdge(edge.toString(), source, target)
		}

		graphStream.display()
	}
}