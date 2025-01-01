package y2015

import Task

//--- Day 22: Wizard Simulator 20XX ---
// I liked this one as the previous one too - however, my apply effects needed to reverse when we decrese the timers for boss turn. I couldn't
// read/understand that from the puzzle input - discovered by testing
class Task22(val input: List<String>):Task {

	private data class Player(
		var hp: Int,
		var mana: Int,
		var armor: Int,    // Current armor (usually 0 or 7 if Shield is active)
		var shield: Int,   // Shield timer (turns left)
		var poison: Int,   // Poison timer (turns left)
		var recharge: Int, // Recharge timer (turns left)
		var spent: Int     // Total mana spent so far
	)
	private data class Boss(var hp: Int, var damage: Int)
	private data class Spell(val name:String, var cost: Int, var damage: Int, var heal: Int, var armor: Int, var mana: Int, var turns: Int)
	private val spells = listOf(
		Spell("Magic Missile", 53, 4, 0, 0, 0, 0),
		Spell("Drain", 73, 2, 2, 0, 0, 0),
		Spell("Shield", 113, 0, 0, 7, 0, 6),
		Spell("Poison", 173, 3, 0, 0, 0, 6),
		Spell("Recharge", 229, 0, 0, 0, 101, 5)
	)

	override fun a(): Int {
		val bossHp = input[0].split(" ")[2].toInt()
		val bossDamage = input[1].split(" ")[1].toInt()
		val boss = Boss(bossHp, bossDamage)
		val player = Player(
			hp = 50,
			mana = 500,
			armor = 0,
			shield = 0,
			poison = 0,
			recharge = 0,
			spent = 0
		)

		val hardMode = false
		val result = play(player, boss, Int.MAX_VALUE, hardMode)
		return result ?: Int.MAX_VALUE // If null, no solution found
	}

	private fun play(
		p: Player,
		b: Boss,
		bestSoFar: Int,
		hardMode: Boolean
	): Int? {
		// --- HARD MODE PENALTY (if enabled) ---
		// The puzzle states: "On hard difficulty, your hero takes 1 damage before every player turn."
		if (hardMode) {
			p.hp--
			if (p.hp <= 0) {
				// If the player dies from the penalty, no solution on this path
				return null
			}
		}

		applyEffects(p, b)

		// If the boss died from Poison or other effects, we succeed immediately
		if (b.hp <= 0) {
			return p.spent
		}

		// If we've already spent more than a known best, prune
		if (p.spent >= bestSoFar) {
			return null
		}

		var minManaToWin: Int? = null

		// 2) Try casting each spell that is allowed (enough mana, not active if it's an effect)
		//The player must choose exactly one spell (if none is affordable/valid, you lose)
		for (spell in spells) {
			//Player turn
			// 3) Clone player & boss so as not to mutate the caller's state
			val newP = p.copy()
			val newB = b.copy()

			// Check if we can cast it:
			if (p.mana < spell.cost) continue  // can't afford
			if (spell.name == "Shield"   && p.shield   > 0) continue // shield already active
			if (spell.name == "Poison"   && p.poison   > 0) continue // poison already active
			if (spell.name == "Recharge" && p.recharge > 0) continue // recharge already active

			// Cast the spell
			newP.mana  -= spell.cost
			newP.spent += spell.cost

			// Apply immediate effects (damage/heal)
			newB.hp -= spell.damage
			newP.hp += spell.heal

			// If the spell starts an effect, set that timer
			when (spell.name) {
				"Shield"    -> newP.shield   = spell.turns
				"Poison"    -> newP.poison   = spell.turns
				"Recharge"  -> newP.recharge = spell.turns
			}

			// If this move itself kills the boss:
			if (newB.hp <= 0) {
				// We won immediately, no boss turn needed
				val costIfWin = newP.spent
				// update minManaToWin
				minManaToWin = if (minManaToWin == null) costIfWin else minOf(minManaToWin, costIfWin)
				continue
			}

			// =============== BOSS TURN ===============

			// 4) Boss turn: apply effects again, then check if boss died from Poison, etc.
			applyEffects2(newP, newB)

			if (newB.hp <= 0) {
				val costIfWin = newP.spent
				minManaToWin = if (minManaToWin == null) costIfWin else minOf(minManaToWin, costIfWin)
				continue
			}

			// Boss attacks the player
			val damage = maxOf(1, newB.damage - newP.armor)
			newP.hp -= damage

			// If the player dies, that path fails
			if (newP.hp <= 0) {
				continue
			}

			/// 9) Otherwise, the fight continues; recursively explore next Player turn
			val currentBest = minManaToWin ?: bestSoFar
			val childResult = play(newP, newB, currentBest, hardMode)

			if (childResult != null) {
				// We found a winning path in the child branch
				minManaToWin = if (minManaToWin == null) childResult else minOf(minManaToWin, childResult)
			}
		}

		return minManaToWin
	}

	private fun applyEffects(p: Player, b: Boss) {
		// Decrement timers
		if (p.shield > 0)   p.shield--
		if (p.poison > 0)   p.poison--
		if (p.recharge > 0) p.recharge--

		p.armor = if (p.shield > 0) 7 else 0

		if (p.poison > 0) {
			b.hp -= 3
		}
		if (p.recharge > 0) {
			p.mana += 101
		}
	}
	private fun applyEffects2(p: Player, b: Boss) {
		p.armor = if (p.shield > 0) 7 else 0
		if (p.poison > 0) {
			b.hp -= 3
		}
		if (p.recharge > 0) {
			p.mana += 101
		}
		if (p.shield > 0)   p.shield--
		if (p.poison > 0)   p.poison--
		if (p.recharge > 0) p.recharge--
	}

	override fun b(): Int {
		val bossHp = input[0].split(" ")[2].toInt()
		val bossDamage = input[1].split(" ")[1].toInt()
		val boss = Boss(bossHp, bossDamage)
		val player = Player(
			hp = 50,
			mana = 500,
			armor = 0,
			shield = 0,
			poison = 0,
			recharge = 0,
			spent = 0
		)

		val hardMode = true
		val result = play(player, boss, Int.MAX_VALUE, hardMode)
		return result ?: Int.MAX_VALUE // If null, no solution found
	}
}