package day03

import println
import readInput

fun main() {

    fun areaContainsSymbol(input: List<String>, lines: IntRange, columns: IntRange): Boolean {
        val numberRange = '0'..'9'
        lines.forEach { l ->
            input.getOrNull(l)?.let {
                it.subSequence(maxOf(0, columns.start), minOf(it.length, columns.endInclusive + 1)).forEach {
                    if (it != '.' && it !in numberRange) {
                        return true
                    }

                }
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
        val numberRegex = Regex("\\d+")
        var result = 0
        input.forEachIndexed { index, line ->
            val matches = numberRegex.findAll(line).map { it.value.toInt() to it.range }.toList()
//            println("l$index $line")
            matches.forEach { (nbr, range) ->
                val containsSymbol = areaContainsSymbol(
                    input,
                    IntRange(index - 1, index + 1),
                    IntRange(range.start - 1, range.endInclusive + 1)
                )
//                println("$containsSymbol  $nbr  $range")
                if (containsSymbol) {
                    result += nbr
                }
            }
        }

        return result
    }

    fun findNumbers(input: List<String>, lines: IntRange, columns: IntRange): List<Int> {
        val numberRegex = Regex("\\d+")
        return lines.flatMap { line ->
            input.getOrNull(line)?.let {
                numberRegex.findAll(it)
                    .filter {
                        it.range.start <= columns.endInclusive && columns.start <=  it.range.endInclusive
                    }.map {
                        it.value.toInt()
                    }.toList()
            }.orEmpty()
        }
    }

    fun part2(input: List<String>): Int {
        var result = 0
        input.forEachIndexed { il, line ->
            line.forEachIndexed { ic, c ->
                if (c == '*') {
                    val numbers = findNumbers(input, IntRange(il - 1, il + 1), IntRange(ic - 1, ic + 1))
//                    println("$il $ic $numbers")
                    if (numbers.size == 2) {
                        result += numbers[0] * numbers[1]
                    }
                }
            }

        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("day03/part1_test")
    val part1 = part1(testInput1)
    check(part1 == 4361)

    val part2 = part2(testInput1)
    check(part2 == 467835)


    println("====")
    val input = readInput("day03/input")
    part1(input).println()
    part2(input).println()
}
