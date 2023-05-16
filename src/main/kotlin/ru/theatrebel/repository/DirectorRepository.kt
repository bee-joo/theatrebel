package ru.theatrebel.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.theatrebel.data.director.Director

interface DirectorRepository : JpaRepository<Director, Long> {
    @Query(value = "select d from Director d")
    override fun findAll(): MutableList<Director>
}