package ru.theatrebel.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.theatrebel.data.review.ReviewDto
import ru.theatrebel.service.ReviewService

@RestController
@RequestMapping("/api/reviews")
class ReviewController(private val reviewService: ReviewService) {

    @GetMapping("/{id}")
    @CrossOrigin
    fun getReviewById(@PathVariable id: Long) = reviewService.getReview(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postReview(@RequestBody reviewDto: ReviewDto) = reviewService.postReview(reviewDto)

    @PutMapping("/{id}")
    fun putReview(@PathVariable id: Long, @RequestBody reviewDto: ReviewDto) = reviewService.editReview(id, reviewDto)

    @DeleteMapping("/{id}")
    fun deleteReview(@PathVariable id: Long) = reviewService.deleteReview(id)
}