package day07

import readInput

enum class Rank {
    five, four, full_house, three, two_pair, one_pair, card
}

fun main() {
    fun check(actual: Number, expected: Number) {
        println("actual: $actual")
        if (!actual.equals(expected)) {
            error("expected $expected, actual $actual")
        }
    }

    val cardStrengths =
        (listOf('A', 'K', 'Q', 'J', 'T') + (2..9).reversed())// .reversed()
            .mapIndexed { index, any -> any.toString().toCharArray()[0] to index }.toMap()

    val cardStrengths2 =
        (listOf('A', 'K', 'Q', 'T') + (2..9).reversed() + 'J')// .reversed()
            .mapIndexed { index, any -> any.toString().toCharArray()[0] to index }.toMap()

    //    fun MutableMap<Char, Int>.remove(t: Char){
//        this.remove()
//    }
    fun calculateRank(cards: CharArray, jokers: Boolean): Rank {
        val freq = cards.toList().groupingBy { it }.eachCount().toMutableMap()

        val j = freq.get('J')
        if (jokers && j != null) {
            freq.remove('J')
            return when {
                freq.isEmpty() -> Rank.five
                freq.all { it.value == 1 } && freq.size == 4 -> Rank.one_pair
                freq.any { it.value == 5 - j } -> Rank.five
                freq.any { it.value == 4 - j } -> Rank.four
                freq.any { it.value == 2 } -> {
                    val t = freq.toList().first() { it.second == 2 }.first
                    freq.remove(t)
                    when {
                        freq.any { it.value == 3 - j } -> Rank.full_house
                        freq.all { it.value ==1 }-> Rank.three
                        freq.any { it.value == 2 - j } -> Rank.two_pair
                        else -> Rank.one_pair
                    }
                }
                freq.any { it.value == 3 - j } -> Rank.three

                else -> Rank.card
            }
        }
        return when {
            freq.any { it.value == 5 } -> Rank.five
            freq.any { it.value == 4 } -> Rank.four
            freq.any { it.value == 2 } -> {
                val t = freq.toList().first() { it.second == 2 }.first
                freq.remove(t)
                when {
                    freq.any { it.value == 3 } -> Rank.full_house
                    freq.any { it.value == 2 } -> Rank.two_pair
                    else -> Rank.one_pair
                }
            }

            freq.any { it.value == 3 } -> Rank.three
            else -> Rank.card
        }
    }


    class CamelCards(val cards: CharArray, val bid: Int, val rank: Rank) {
        override fun toString(): String {
            return "CamelCards(cards=${cards.joinToString("")}, bid=$bid, rank=$rank)"
        }


    }

    fun part1(input: List<String>): Int {
        val cards = input.map {
            val (a, b) = it.split(" ")
            val cards = a.toCharArray()
            val c = CamelCards(cards, b.toInt(), calculateRank(cards, false))

            c
        }
//        cardStrengths.forEach { println(it) }

        val nbrOfCards = cards.size
        return cards.sortedWith(
            compareBy(
                { it.rank },
                { cardStrengths[it.cards[0]] },
                { cardStrengths[it.cards[1]] },
                { cardStrengths[it.cards[2]] },
                { cardStrengths[it.cards[3]] },
                { cardStrengths[it.cards[4]] },
            )
        ).mapIndexed { index, it ->
            val t = (nbrOfCards - index) * it.bid
//                println(""+  t + " " + it.toString())
            t
        }.sumOf { it }
    }

    fun part2(input: List<String>): Int {
        val cards = input.map {
            val (a, b) = it.split(" ")
            val cards = a.toCharArray()
            val c = CamelCards(cards, b.toInt(), calculateRank(cards, true))

            c
        }
//        cardStrengths2.forEach { println(it) }

        val nbrOfCards = cards.size
        return cards.sortedWith(
            compareBy(
                { it.rank },
                { cardStrengths2[it.cards[0]] },
                { cardStrengths2[it.cards[1]] },
                { cardStrengths2[it.cards[2]] },
                { cardStrengths2[it.cards[3]] },
                { cardStrengths2[it.cards[4]] },
            )
        ).mapIndexed { index, it ->
            val t = (nbrOfCards - index) * it.bid
            //  println("$t\t$it")
            t
        }.sumOf { it }
    }

    val testInput1 = readInput("day07/test")
    val input = readInput("day07/input")

    val part1 = part1(testInput1)
    check(part1, 6440)

    val solution1 = part1(input)
    check(solution1, 249726565)
//
    println("====")
listOf("TTAAJ", "JJABC", "JAABB", "JJAAB", "AAJ87").forEach {

    val r = calculateRank(it.toCharArray(), true)
    println("$it $r")
}

//
    val part2 = part2(testInput1)
    check(part2, 5905)

    val solution2 = part2(input)

    check(solution2, 251135960)
}
