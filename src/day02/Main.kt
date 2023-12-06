package day02

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
      return 0
    }

    fun part2(input: List<String>): Int {
       return 0

    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("day02/part1_test")
    val part1 = part1(testInput1)
    check(part1 == 142)

    val testInput2 = readInput("day02/part2_test")

    val part2 = part2(testInput2)
    check(part2 == 281)
    val part3 = part2(listOf("58ninehxcsnzfxbf6xvgcrfznrldqntsbsjmr5"))
    check(part3 == 55)



println("====")
    val input = readInput("day02/input")
    part1(input).println()
    part2(input).println()
}
