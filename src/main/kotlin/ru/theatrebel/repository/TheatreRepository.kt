package ru.theatrebel.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.theatrebel.data.theatre.Theatre

interface TheatreRepository : JpaRepository<Theatre, Long> {
    @Query(value = "select t from Theatre t")
    override fun findAll(): MutableList<Theatre>
}