package day02

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val maxCubes = mapOf("red" to 12,"green" to 13, "blue" to 14)

       return input.sumOf {
            val game = it.split(":")[0]

            val gameId = game.split(" ")[1].toInt()
            val maxByColor = mutableMapOf<String, Int>()
            val subsets = it.split(":")[1].trim().split(";")

            subsets.forEachIndexed { index, it ->
                it.split(",").map { it.trim() }.map {
                    val nbr = it.split(" ")[0].toInt()
                    val color = it.split(" ")[1]
                   // println("$game subset $index:  $nbr $color")

                    maxByColor.put(color, maxOf(maxByColor.getOrDefault(color, 0), nbr))
                }
            }
//            println("$gameId $maxByColor")

          val ok =  maxCubes.none { (color, max) ->
                maxByColor.getOrDefault(color, 0) > max }
            if(ok) gameId else 0
        }
    }

    fun part2(input: List<String>): Int {
        val maxCubes = mapOf("red" to 12,"green" to 13, "blue" to 14)

        return input.sumOf {
            val game = it.split(":")[0]

            val gameId = game.split(" ")[1].toInt()
            val minByColor = mutableMapOf<String, Int>()
            val subsets = it.split(":")[1].trim().split(";")

            subsets.forEachIndexed { index, it ->
                it.split(",").map { it.trim() }.map {
                    val nbr = it.split(" ")[0].toInt()
                    val color = it.split(" ")[1]
                   // println("$game subset $index:  $nbr $color")

                    minByColor.put(color, maxOf(minByColor.getOrDefault(color, nbr), nbr))
                }
            }

            var t = 1
            minByColor.forEach { t*=it.value }
println(t)

            t
        }

    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("day02/part1_test")
    val part1 = part1(testInput1)
    check(part1 == 8)

    val part2 = part2(testInput1)
    check(part2 == 2286)


    println("====")
    val input = readInput("day02/input")
    part1(input).println()
    part2(input).println()
}
