//Time:        59     79     65     75
//Distance:   597   1234   1032   1328
class Race(val time: Int, val distance: Int){}
val races = listOf(Race(59, 597),Race(79,1234),Race(65,1032),Race(75,1328)  )

class Uppg6 {

	fun a(){
		var result = 1;
		for (race in races) {
			result *= findWays(race);
		}
		println(result)
	}

	private fun findWays(race: Race): Int {
		var ways = 0
		for (i in 1..race.time){
			if(i * (race.time - i) > race.distance){
				ways++
			}
		}
		return ways
	}
}