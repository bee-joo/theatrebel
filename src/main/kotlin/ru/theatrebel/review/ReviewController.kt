package ru.theatrebel.review

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reviews")
class ReviewController(private val reviewRepository: ReviewRepository) {

    @GetMapping("/{id}")
    fun getReviewById(@PathVariable id: Long) = reviewRepository.findById(id)

    @PostMapping
    fun postReview(@RequestBody review: Review) = reviewRepository.save(review)
}