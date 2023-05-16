package ru.theatrebel

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication
class TheatrebelApplication {
    @Bean
    fun hibernateModule(): Module {
        return Hibernate5Module()
    }
}

fun main(args: Array<String>) {
    runApplication<TheatrebelApplication>(*args)
}
