package ru.theatrebel.unit

import kotlin.random.Random

const val STRING_LENGTH = 100
val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun getRandomString() = (1..STRING_LENGTH)
    .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
    .joinToString("")