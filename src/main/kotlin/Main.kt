import y2023.Uppg12
import y2023.Uppg13
import kotlin.system.measureTimeMillis


fun main(args: Array<String>) {
	val executionTime = measureTimeMillis {
		val task = Uppg13();
		val result = task.a();
		println("Execution result: $result")
	}
	println("Execution time: $executionTime ms")
}



