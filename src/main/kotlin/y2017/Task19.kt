package y2017

import Task
import utils.Point2D
import utils.toGrid

//--- Day 19: A Series of Tubes ---
class Task19 (val input:List<String>) :Task {

	override fun a(): Any {
		val grid = toGrid(input)
		//find start
		val point = Point2D()
		for(i in grid[0].indices){
			if(grid[0][i].third == '|'){
				point.x = 0
				point.y = i
				point.direction = Point2D.Direction.SOUTH
			}
		}
		var letters = ""
		var steps = 0
		while(true){
			steps++
			point.move(1)
			if(point.x !in grid.indices || point.y !in grid[0].indices){
				break
			}
			val c = grid[point.x][point.y].third
			if(c == ' '){
				break
			}
			if(c in ('A'..'Z')){
				letters += c
			}
			if(c == '+'){
				val neighbors = point.getCardinalNeighbors()
				for(n in neighbors){
					//check if neighbor is valid and also not the one that we came from (opposite direction)
					if(n.x in grid.indices && n.y in grid[0].indices && grid[n.x][n.y].third != ' ' && n != point.getPreviousOpositeDirection()){
						point.face(point.getRelativeDirection(n))
						break
					}
				}
			}
//			printGridColor(grid, listOf(' ','|','+','-'), ('A'..'Z').toList(), point.toPair())
		}

		return letters
	}

	override fun b(): Any {
		val grid = toGrid(input)
//		printGridColor(grid, listOf(' ','|','+','-'), ('A'..'Z').toList())
		//find start
		val point = Point2D()
		for(i in grid[0].indices){
			if(grid[0][i].third == '|'){
				point.x = 0
				point.y = i
				point.direction = Point2D.Direction.SOUTH
			}
		}
		var letters = ""
		var steps = 0
		while(true){
			steps++
			point.move(1)
			if(point.x !in grid.indices || point.y !in grid[0].indices){
				break
			}
			val c = grid[point.x][point.y].third
			if(c == ' '){
				break
			}
			if(c in ('A'..'Z')){
				letters += c
			}
			if(c == '+'){
				val neighbors = point.getCardinalNeighbors()
				for(n in neighbors){
					//check if neighbor is valid and also not the one that we came from (opposite direction)
					if(n.x in grid.indices && n.y in grid[0].indices && grid[n.x][n.y].third != ' ' && n != point.getPreviousOpositeDirection()){
						point.face(point.getRelativeDirection(n))
						break
					}
				}
			}
//			printGridColor(grid, listOf(' ','|','+','-'), ('A'..'Z').toList(), point.toPair())
		}

		return steps
	}
}