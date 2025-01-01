package y2015

import Task
import kotlin.math.max

//--- Day 21: RPG Simulator 20XX ---
class Task21(val input: List<String>):Task {

	data class Player(val name:String, var hitPoints: Int, var damage: Int, var armor: Int)
	data class Gear(val name: String, val cost:Int, val damage: Int, val armor:Int)

	override fun a(): Any {
		val boss = getBoss(input)
		val player = Player("nerdman",100, 0, 0)
		val attack = getAttackGear()
		val armor = getArmorGear()
		val rings = getRingGear()
		val bestGear = mutableListOf<Gear>()
		val currentGear = mutableListOf<Gear>()
		findCheapGear(attack, armor, rings, player, boss, bestGear, 0,0,0,0,0,0, currentGear)
		return bestGear.sumOf { it.cost }
	}

	private fun getRingGear(): Set<Gear> {
		return setOf(
			Gear("Damage +1", 25, 1, 0),
			Gear("Damage +2", 50, 2, 0),
			Gear("Damage +3", 100, 3, 0),
			Gear("Defense +1", 20, 0, 1),
			Gear("Defense +2", 40, 0, 2),
			Gear("Defense +3", 80, 0, 3)
		)
	}
	private fun getArmorGear(): Set<Gear> {
		return setOf(
			Gear("Leather", 13, 0, 1),
			Gear("Chainmail", 31, 0, 2),
			Gear("Splintmail", 53, 0, 3),
			Gear("Bandedmail", 75, 0, 4),
			Gear("Platemail", 102, 0, 5)
		)
	}
	private fun getAttackGear(): Set<Gear> {
		return setOf(
			Gear("Dagger", 8, 4, 0),
			Gear("Shortsword", 10, 5, 0),
			Gear("Warhammer", 25, 6, 0),
			Gear("Longsword", 40, 7, 0),
			Gear("Greataxe", 74, 8, 0)
		)
	}

	private fun findCheapGear(
		attack: Set<Gear>,
		armor: Set<Gear>,
		rings: Set<Gear>,
		player: Player,
		boss: Player,
		bestGear: MutableList<Gear>,
		indexAttack: Int = 0, indexArmor: Int = 0, indexRings: Int = 0,
		pickedWeaponCount: Int = 0,	pickedArmorCount:Int = 0, pickedRingsCount: Int = 0,
		current: MutableList<Gear> = mutableListOf()) {
		// Base Case: if we passed the last item in all categories
		if (indexAttack == attack.size && indexArmor == armor.size && indexRings == rings.size) {
			// Check if exactly 1 weapon, <=1 armor, <=2 rings, and if boss is defeated
			if (pickedWeaponCount == 1 && pickedArmorCount <= 1 && pickedRingsCount <= 2) {
				if (playerBeatsBoss(player, boss, current)) {
					val cost = current.sumOf { it.cost }
					if (bestGear.isEmpty() || cost < bestGear.sumOf { it.cost }) {
						bestGear.clear()
						bestGear.addAll(current)
//						println("New best gear: $bestGear with cost $cost")
					}
				}
			}
			return
		}

		// Include case
		//	You must buy exactly one weapon; no dual-wielding. Armor is optional, but you can't use more than one. You can buy 0-2 rings (at most one for
		//  each hand). You must use any items you buy. The shop only has one of each item, so you can't buy, for example, two rings of Damage +3.
		if (indexAttack < attack.size) {
			//include weapon only if no weapon is picked yet
			if (pickedWeaponCount == 0) {
				current.add(attack.elementAt(indexAttack))
				findCheapGear(attack, armor, rings, player, boss, bestGear, indexAttack + 1, indexArmor, indexRings, pickedWeaponCount + 1, pickedArmorCount, pickedRingsCount, current)
				current.removeAt(current.size - 1)
			}
			//try another if possible
			findCheapGear(attack, armor, rings, player, boss, bestGear, indexAttack + 1, indexArmor, indexRings, pickedWeaponCount, pickedArmorCount, pickedRingsCount, current)
			return
		}
		if(indexArmor < armor.size) {
			//include 0-1 armor
			if(pickedArmorCount == 0) {
				current.add(armor.elementAt(indexArmor))
				findCheapGear(attack, armor, rings, player, boss, bestGear, indexAttack, indexArmor + 1, indexRings, pickedWeaponCount, pickedArmorCount + 1, pickedRingsCount, current)
				current.removeAt(current.size -1)
			}
			//try another if possible
			findCheapGear(attack, armor, rings, player, boss, bestGear, indexAttack, indexArmor + 1, indexRings, pickedWeaponCount, pickedArmorCount, pickedRingsCount, current)
			return
		}

		if(indexRings < rings.size) {
			//include up to 2 rings
			if(pickedRingsCount < 2) {
				current.add(rings.elementAt(indexRings))
				findCheapGear(attack, armor, rings, player, boss, bestGear, indexAttack, indexArmor, indexRings + 1, pickedWeaponCount, pickedArmorCount, pickedRingsCount + 1, current)
				current.removeAt(current.size -1)
			}
			//try another if possible
			findCheapGear(attack, armor, rings, player, boss, bestGear, indexAttack, indexArmor, indexRings+1, pickedWeaponCount, pickedArmorCount, pickedRingsCount, current)
			return
		}
	}

	private fun playerBeatsBoss(player: Player, boss: Player, current: MutableList<Gear>): Boolean {
		val playerCopy = player.copy()
		val bossCopy = boss.copy()
		current.forEach {
			playerCopy.damage += it.damage
			playerCopy.armor += it.armor
		}
		val winner = playOneMatch(playerCopy, bossCopy)
		return winner == playerCopy
	}

	private fun playOneMatch(player: Player, boss: Player): Player {
		var playerTurn = true //player goes always first
		while (player.hitPoints > 0 && boss.hitPoints > 0) {
			if (playerTurn) {
				//damage is min 1
				val damage = max(1,player.damage - boss.armor)
				boss.hitPoints -= damage
//				println("Player hits boss for $damage, boss has ${boss.hitPoints} left")
			} else {
				val damage = max(1, boss.damage - player.armor)
				player.hitPoints -= damage
//				println("Boss hits player for $damage, player has ${player.hitPoints} left")
			}
			playerTurn = !playerTurn
		}
		return if (player.hitPoints > 0) player else boss
	}

	private fun getBoss(input: List<String>): Player {
		val hitPoints = input[0].split(": ")[1].toInt()
		val damage = input[1].split(": ")[1].toInt()
		val armor = input[2].split(": ")[1].toInt()
		return Player("bossman", hitPoints, damage, armor)
	}

	override fun b(): Any {
		val boss = getBoss(input)
		val player = Player("jeff",100, 0, 0)
		val attack = getAttackGear()
		val armor = getArmorGear()
		val rings = getRingGear()
		val worstGear = mutableListOf<Gear>()
		val currentGear = mutableListOf<Gear>()
		findWorstGear(attack, armor, rings, player, boss, worstGear, 0,0,0,0,0,0, currentGear)
		return worstGear.sumOf { it.cost }
	}

	private fun findWorstGear(
		attack: Set<Gear>,
		armor: Set<Gear>,
		rings: Set<Gear>,
		player: Player,
		boss: Player,
		worstGear: MutableList<Gear>,
		indexAttack: Int = 0, indexArmor: Int = 0, indexRings: Int = 0,
		pickedWeaponCount: Int = 0, pickedArmorCount:Int = 0, pickedRingsCount: Int = 0,
		current: MutableList<Gear> = mutableListOf()) {
		// Base Case: if we passed the last item in all categories
		if (indexAttack == attack.size && indexArmor == armor.size && indexRings == rings.size) {
			// Check if exactly 1 weapon, <=1 armor, <=2 rings, and if boss is defeated
			if (pickedWeaponCount == 1 && pickedArmorCount <= 1 && pickedRingsCount <= 2) {
				if (!playerBeatsBoss(player, boss, current)) {
					val cost = current.sumOf { it.cost }
					if (worstGear.isEmpty() || cost > worstGear.sumOf { it.cost }) {
						worstGear.clear()
						worstGear.addAll(current)
//						println("New worst gear: $worstGear with cost $cost")
					}
				}
			}
			return
		}

		if (indexAttack < attack.size) {
			//include weapon only if no weapon is picked yet
			if (pickedWeaponCount == 0) {
				current.add(attack.elementAt(indexAttack))
				findWorstGear(attack, armor, rings, player, boss, worstGear, indexAttack + 1, indexArmor, indexRings, pickedWeaponCount + 1, pickedArmorCount, pickedRingsCount, current)
				current.removeAt(current.size - 1)
			}
			//try another if possible
			findWorstGear(attack, armor, rings, player, boss, worstGear, indexAttack + 1, indexArmor, indexRings, pickedWeaponCount, pickedArmorCount, pickedRingsCount, current)
			return
		}
		if(indexArmor < armor.size) {
			//include 0-1 armor
			if(pickedArmorCount == 0) {
				current.add(armor.elementAt(indexArmor))
				findWorstGear(attack, armor, rings, player, boss, worstGear, indexAttack, indexArmor + 1, indexRings, pickedWeaponCount, pickedArmorCount + 1, pickedRingsCount, current)
				current.removeAt(current.size -1)
			}
			//try another if possible
			findWorstGear(attack, armor, rings, player, boss, worstGear, indexAttack, indexArmor + 1, indexRings, pickedWeaponCount, pickedArmorCount, pickedRingsCount, current)
			return
		}

		if(indexRings < rings.size) {
			//include up to 2 rings
			if(pickedRingsCount < 2) {
				current.add(rings.elementAt(indexRings))
				findWorstGear(attack, armor, rings, player, boss, worstGear, indexAttack, indexArmor, indexRings + 1, pickedWeaponCount, pickedArmorCount, pickedRingsCount + 1, current)
				current.removeAt(current.size -1)
			}
			//try another if possible
			findWorstGear(attack, armor, rings, player, boss, worstGear, indexAttack, indexArmor, indexRings+1, pickedWeaponCount, pickedArmorCount, pickedRingsCount, current)
			return
		}
	}
}