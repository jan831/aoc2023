package day05


import kotlinx.coroutines.*
import readInput
import kotlin.math.min

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    fun check(actual: Number, expected: Number) {
        println("actual: $actual")
        if (!actual.equals(expected)) {
            error("expected $expected, actual $actual")
        }
    }

    data class RangeInfo(val destinationStart: Long, val sourceStart: Long, val length: Long) {

            val asRange = LongRange(sourceStart, sourceStart + length - 1)
        fun match(source: Long): Long? {
            return if (source in asRange) {
                // println("$source -> ${ source - sourceStart + destinationStart}")
                source - sourceStart + destinationStart
            } else
                null
        }
    }

    data class RangeInfoMap(val identifier: String, val ranges: List<RangeInfo>) {
        fun convert(source: Long): Long {
          val r =  ranges.dropWhile {  it.sourceStart > source }
            val t = r.firstNotNullOfOrNull { it.match(source) }

            // println("$source -> $t  $identifier")
            return t ?: source
        }
    }

    data class SeedInfo(val seeds: List<Long>, val maps: MutableList<RangeInfoMap> = mutableListOf()) {

        fun convert(source: Long): Long {
            val value = maps.fold(source) { v, m -> m.convert(v) }
            return value
        }
    }


    fun parse(input: List<String>): SeedInfo {
        val seeds = input.first().removePrefix("seeds: ").split(" ").map { it.toLong() }

        val seedInfo = SeedInfo(seeds)

        val it = (input.drop(2) + "").iterator()

        while (it.hasNext()) {
            val map = it.next().removeSuffix(" map:")
            var next = it.next()
            var rangeInfo = mutableListOf<RangeInfo>()
            while (next.isNotBlank()) {
                val (d, s, l) = next.split(" ").map { it.toLong() }
                rangeInfo += RangeInfo(d, s, l)
                next = it.next()
            }

            seedInfo.maps += RangeInfoMap(map, rangeInfo.sortedBy { it.sourceStart }.reversed())
        }
        return seedInfo
    }

    fun part1(input: List<String>): Long {
        val seedInfo = parse(input)


        return seedInfo.seeds.minOf { seedInfo.convert(it) }
    }

    fun part2(input: List<String>): Long {
        val seedInfo = parse(input)

        val chunked = seedInfo.seeds.chunked(2)
        println("chunks: ${chunked.size}")
        chunked.forEach { (start, length) ->
            println(" $start $length")
        }

//        return chunked.map { (start, length) ->
//            val t = LongRange(start, start + length - 1).fold(Long.MAX_VALUE) { s, seed ->
//                min(s, seedInfo.convert(seed))
//            }
//            // println("size ${t.size} ${t.distinct().size}")
//            // t.min()
//            t
//        }.min()

        val d = chunked.map { (start, length) ->

            GlobalScope.async {

            val t = LongRange(start, start + length - 1).fold(Long.MAX_VALUE) { s, seed ->
                min(s, seedInfo.convert(seed))
            }

                println("finished")
            // println("size ${t.size} ${t.distinct().size}")
            // t.min()
            t
            }
        }

        return runBlocking {

        d.awaitAll()

         d.map { it.await() }.min()
        }
    }

    val testInput1 = readInput("day05/part1_test")
    val input = readInput("day05/input")


    val part1 = part1(testInput1)
    check(part1, 35L)

    val solution1 = part1(input)
    check(solution1, 836040384L)

    println("====")

    val part2 = part2(testInput1)
    check(part2, 46L)

    val solution2 = part2(input)
    check(solution2, 10834440)

}
