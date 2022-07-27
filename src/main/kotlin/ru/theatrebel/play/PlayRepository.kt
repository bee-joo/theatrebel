package ru.theatrebel.play

import org.springframework.data.jpa.repository.JpaRepository

interface PlayRepository : JpaRepository<Play, Long> {
}