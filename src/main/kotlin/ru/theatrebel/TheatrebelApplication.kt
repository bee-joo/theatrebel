package ru.theatrebel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TheatrebelApplication

fun main(args: Array<String>) {
    runApplication<TheatrebelApplication>(*args)
}
