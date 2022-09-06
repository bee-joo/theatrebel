package ru.theatrebel.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.theatrebel.dto.ReviewDto
import ru.theatrebel.service.ReviewService

@RestController
@RequestMapping("/api/reviews")
class ReviewController(private val reviewService: ReviewService) {

    @GetMapping("/{id}")
    fun getReviewById(@PathVariable id: Long) = reviewService.getReview(id)

    @PostMapping
    fun postReview(@RequestBody reviewDto: ReviewDto) = reviewService.postReview(reviewDto)

    @PatchMapping("/{id}")
    fun patchReview(@PathVariable id: Long, @RequestBody reviewDto: ReviewDto) = reviewService.editReview(id, reviewDto)

    @DeleteMapping("/{id}")
    fun deleteReview(@PathVariable id: Long) = reviewService.deleteReview(id)
}