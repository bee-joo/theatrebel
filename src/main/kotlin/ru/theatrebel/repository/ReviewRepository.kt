package ru.theatrebel.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.theatrebel.entity.Review

interface ReviewRepository : JpaRepository<Review, Long> {
    fun findAllByPlayId(id: Long): Set<Review>
}