package y2017

import Task
import utils.isOnlyLetters

//--- Day 18: Duet ---
class Task18(val input:List<String>):Task {
	private data class Register(val name:String,var value:Long = 0, var lastSound:Long = 0)
	private data class Instruction(val type:String,val x:String,val y:String)
	override fun a(): Any {
		val registers = mutableMapOf<String,Register>()
		val instructions = mutableListOf<Instruction>()
		var lastSound = 0L
		for(line in input){
			println(line)
			val parts = line.split(" ")
			val type = parts[0]
			val x = parts[1]
			val y = if(parts.size>2) parts[2] else ""
			if(x !in registers) registers[x] = Register(x)
			if(y !in registers && y.isOnlyLetters() ) registers[y] = Register(y)
			instructions.add(Instruction(type,x,y))
		}
		var i = 0
		while(i in instructions.indices){
			val instruction = instructions[i]
			val x = instruction.x
			val y = instruction.y
			var registerX = registers[x]
			if(registerX==null){
				registerX = Register(x)
				registers[x] = registerX
			}
			val valueY = if(y.toLongOrNull()!=null) y.toLong() else registers[y]!!.value
			when(instruction.type){
				"set" -> registerX.value = valueY
				"add" -> registerX.value += valueY
				"mul" -> registerX.value *= valueY
				"mod" -> registerX.value %= valueY
				"snd" -> lastSound = registerX.value
				"rcv" -> if(registerX.value!=0L) return lastSound
				"jgz" -> if(registerX.value>0) i += valueY.toInt()-1
			}
			i++
		}

		return 0
	}

	override fun b(): Any {
		return 0
	}
}