package day06

import readInput

fun main() {

    data class Race(val duration: Int, val maxDistance: Int)

    fun check(actual: Number, expected: Number) {
        println("actual: $actual")
        if (!actual.equals(expected)) {
            error("expected $expected, actual $actual")
        }
    }

    fun parse(input: List<String>): List<Race> {
        val times = input[0].split(" ").drop(1).filter { it.isNotBlank() }.map { it.trim().toInt() }
        val distances = input[1].split(" ").drop(1).filter { it.isNotBlank() }.map { it.trim().toInt() }

        return times.zip(distances).map { Race(it.first, it.second) }
    }

    fun calculate(races: List<Race>): Int {
        val t = races.fold(1) { value, race ->
            val start = race.maxDistance / race.duration
            val first = (start..race.duration - start).first { w ->
                val distance = w * (race.duration - w)
                distance > race.maxDistance
            }
            val last = (start..race.duration - start).last { w ->
                val distance = w * (race.duration - w)
                distance > race.maxDistance
            }
            println("${last - first}  $race  $start")
            value * (last - first + 1)
        }
        return t
    }

    fun part1(input: List<String>): Int {

        val races = parse(input)

        val t = calculate(races)
        return t
    }

    fun part2(input: List<String>): Int {

        val duration = input[0].split(":").last().replace(" ", "").toInt()
        val distance = input[1].split(":").last().replace(" ", "").toInt()
        val race = Race(duration, distance)
        return calculate(listOf(race))
    }


    val testInput1 = readInput("day06/test")
    val input = readInput("day06/input")

    val part1 = part1(testInput1)
    check(part1, 288)

    val solution1 = part1(input)
    check(solution1, 2756160)

    println("====")

    val part2 = part2(testInput1)
    check(part2, 0)

    val solution2 = part2(input)
    check(solution2, 0)
}
