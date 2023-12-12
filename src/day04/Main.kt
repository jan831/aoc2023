package day04

import println
import readInput

fun main() {


    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { line ->
            val card = line.substring(0, line.indexOf(':'))
            val (winning, own) = line.substring(line.indexOf(':') + 1).split("|")
                .map { it.trim().split(" ").map { it.trim() }.filter { it.isNotBlank() } }
            println(card)
            println(" own ${own.sorted()}")
            println(" winning ${winning.sorted()}")

            var r = 0.5
            winning.forEach {
                if (it in own) {
                    println("  match $it")
                    r *= 2
                }
            }
            if(r > 0.6){
                println(" add to result $r")
                result += r.toInt()
            }
        }
        return result.toInt()
    }

    data class Card(val win: List<String>, val own:List<String>, var nbrOfCards:Int = 1){
        val wins: Int = win.count{ it in own}
    }

    fun part2(input: List<String>): Int {
        var result = 0
        val winOwn = mutableListOf<Card> ()
        input.forEach { line ->
            val card = line.substring(0, line.indexOf(':'))
            val (winning, own) = line.substring(line.indexOf(':') + 1).split("|")
                .map { it.trim().split(" ").map { it.trim() }.filter { it.isNotBlank() } }
            println(card)
            println(" own ${own.sorted()}")
            println(" winning ${winning.sorted()}")
            winOwn += Card( own , winning)
        }


        winOwn.forEachIndexed { index, card ->
            winOwn.subList(index+1, index+ 1+ card.wins).forEach {
                 it.nbrOfCards += card.nbrOfCards
            }
            card.wins
        }

        return winOwn.sumOf { it.nbrOfCards }
//        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("day04/part1_test")
    val part1 = part1(testInput1)
    check(part1 == 13)

    val part2 = part2(testInput1)
    check(part2 == 30)


    println("====")
    val input = readInput("day04/input")
    part1(input).println()
    part2(input).println()
}
