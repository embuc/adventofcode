import y2023.*
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
	val executionTime = measureTimeMillis {
		val task = Task18
		val result = task.a()
		println("Execution result: $result")
	}
	println("Execution time: $executionTime ms")
}



