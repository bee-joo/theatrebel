package ru.theatrebel.service

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.theatrebel.data.ResponseObject
import ru.theatrebel.data.review.Review
import ru.theatrebel.data.review.ReviewDto
import ru.theatrebel.data.review.toEntity
import ru.theatrebel.data.review.update
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.exception.ValidationException
import ru.theatrebel.repository.PlayRepository
import ru.theatrebel.repository.ReviewRepository

interface ReviewService {
    fun getReview(id: Long): Review
    fun postReview(reviewDto: ReviewDto): Review
    fun deleteReview(id: Long): ResponseObject<String>
    fun editReview(id: Long, reviewDto: ReviewDto): Review
}

@Service
class ReviewServiceImpl(private val reviewRepository: ReviewRepository,
                        private val playRepository: PlayRepository
) : ReviewService {

    override fun getReview(id: Long): Review {
        return reviewRepository.findById(id)
            .orElseThrow { throw NotFoundException("Review with id $id not found!") }
    }

    override fun postReview(reviewDto: ReviewDto): Review {
        if (reviewDto.playId == null || !playRepository.existsById(reviewDto.playId!!)) {
            throw ValidationException("No play found")
        }

        return try {
            reviewRepository.save(reviewDto.toEntity())
        } catch (e: ValidationException) {
            throw e
        }
    }

    override fun deleteReview(id: Long): ResponseObject<String> {
        if (!reviewRepository.existsById(id)) {
            throw NotFoundException("Review with id $id doesn't exist")
        }

        reviewRepository.deleteById(id)
        return ResponseObject(HttpStatus.OK.value(), "Deleted")
    }

    override fun editReview(id: Long, reviewDto: ReviewDto): Review {
        val review = try {
            reviewRepository.getReferenceById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw e
        }

        return reviewRepository.save(review.update(reviewDto))
    }
}