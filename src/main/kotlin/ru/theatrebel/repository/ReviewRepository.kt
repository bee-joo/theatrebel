package ru.theatrebel.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.theatrebel.data.review.Review

interface ReviewRepository : JpaRepository<Review, Long> {
    fun findAllByPlayId(id: Long): List<Review>
}