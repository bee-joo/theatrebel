package ru.theatrebel.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.theatrebel.entity.Play

interface PlayRepository : JpaRepository<Play, Long> {
}