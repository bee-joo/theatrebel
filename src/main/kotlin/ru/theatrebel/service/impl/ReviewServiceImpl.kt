package ru.theatrebel.service.impl

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import ru.theatrebel.dto.ReviewDto
import ru.theatrebel.dto.toEntity
import ru.theatrebel.entity.Review
import ru.theatrebel.entity.update
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.exception.ValidationException
import ru.theatrebel.repository.PlayRepository
import ru.theatrebel.repository.ReviewRepository
import ru.theatrebel.service.ReviewService

@Service
class ReviewServiceImpl(private val reviewRepository: ReviewRepository,
                        private val playRepository: PlayRepository) : ReviewService {

    override fun getReview(id: Long): Review {
        return reviewRepository.findById(id)
            .orElseThrow { throw NotFoundException("Review with id $id not found!") }
    }

    override fun postReview(reviewDto: ReviewDto): Review {
        if (reviewDto.playId == null || !playRepository.existsById(reviewDto.playId!!)) {
            throw ValidationException("No play found")
        }

        return reviewRepository.save(reviewDto.toEntity())
    }

    override fun deleteReview(id: Long) {
        try {
            reviewRepository.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw e
        }
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