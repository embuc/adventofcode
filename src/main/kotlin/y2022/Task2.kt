package y2022

import Task

//--- Day 2: Rock Paper Scissors ---
class Task2(val input: List<String>) : Task {
	// a - Rock, b - Paper, c - Scissors
	// x - rock, y - paper, z - scissors

	override fun a(): Int {
		var sum = 0
		for (line in input) {
			val (p1, p2) = line.split(" ")
			if (p1 == "A" && p2 == "X") {
				sum += 1 + 3// rock vs rock (draw)
			} else if (p1 == "B" && p2 == "X") {
				sum += 1 // paper vs rock (lose)
			} else if (p1 == "C" && p2 == "X") {
				sum += 1 + 6// scissors vs rock (win)
			} else if (p1 == "A" && p2 == "Y") {
				sum += 2 + 6 // rock vs paper(win)
			} else if (p1 == "B" && p2 == "Y") {
				sum += 2 + 3 // paper vs paper (draw)
			} else if (p1 == "C" && p2 == "Y") {
				sum += 2 // scissors vs paper (lose)
			} else if (p1 == "A" && p2 == "Z") {
				sum += 3 // rock vs scissors (lose)
			} else if (p1 == "B" && p2 == "Z") {
				sum += 3 + 6 //paper vs scissors (win)
			} else if (p1 == "C" && p2 == "Z") {
				sum += 3 + 3 // scissors vs scissors (draw)
			}
		}
		return sum
	}

	override fun b(): Any {
		var sum = 0
		// x lose, y draw, z win
		for (line in input) {
			val (p1, p2) = line.split(" ")
			if (p1 == "A" && p2 == "X") {
				sum += 3// rock vs scissors (lose)
			} else if (p1 == "B" && p2 == "X") {
				sum += 1 // paper vs rock (lose)
			} else if (p1 == "C" && p2 == "X") {
				sum += 2 // scissors vs paper (lose)
			} else if (p1 == "A" && p2 == "Y") {
				sum += 1 + 3  // rock vs rock(draw)
			} else if (p1 == "B" && p2 == "Y") {
				sum += 2 + 3 // paper vs paper (draw)
			} else if (p1 == "C" && p2 == "Y") {
				sum += 3 + 3 // scissors vs scissors (draw)
			} else if (p1 == "A" && p2 == "Z") {
				sum += 2 + 6 // rock vs paper (win)
			} else if (p1 == "B" && p2 == "Z") {
				sum += 3 + 6 //paper vs scissors (win)
			} else if (p1 == "C" && p2 == "Z") {
				sum += 1 + 6 // scissors vs rock (win)
			}
		}
		return sum
	}
}
