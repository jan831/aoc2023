package day01

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val numberRange = '0'..'9'
        return input.sumOf {
            10 * (it.first { it in numberRange } - '0') + (it.last { it in numberRange } - '0')
        }
    }

    fun part2(input: List<String>): Int {
        val numbers = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        ) + (1..9).map { it.toString() to it }

       return  input.sumOf {
            val first =  numbers.map {n -> n.value to it.indexOf(n.key) }.filter { it.second > -1 }.minBy { it.second }.first
            val last = numbers.maxBy {n -> it.lastIndexOf(n.key) }.value
            10*first + last
        }

    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("day01/part1_test")
    val part1 = part1(testInput1)
    check(part1 == 142)

    val testInput2 = readInput("day01/part2_test")

    val part2 = part2(testInput2)
    check(part2 == 281)
    val part3 = part2(listOf("58ninehxcsnzfxbf6xvgcrfznrldqntsbsjmr5"))
    check(part3 == 55)



println("====")
    val input = readInput("day01/Day01")
    part1(input).println()
    part2(input).println()
}
