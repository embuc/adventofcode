import y2023.*
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
	val executionTime = measureTimeMillis {
		val task = Task16;
		val result = task.b();
		println("Execution result: $result")
	}
	println("Execution time: $executionTime ms")
}



