package ru.theatrebel.service

import ru.theatrebel.dto.ReviewDto
import ru.theatrebel.entity.Review

interface ReviewService {
    fun getReview(id: Long): Review
    fun postReview(reviewDto: ReviewDto): Review
    fun deleteReview(id: Long)
    fun editReview(id: Long, reviewDto: ReviewDto): Review
}