import y2023.Task14
import y2023.Uppg12
import y2023.Uppg13
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
	val executionTime = measureTimeMillis {
		val task = Task14;
		val result = task.b();
		println("Execution result: $result")
	}
	println("Execution time: $executionTime ms")
}



