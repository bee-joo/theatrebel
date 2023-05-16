package ru.theatrebel.unit

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import ru.theatrebel.data.review.Review
import ru.theatrebel.data.review.ReviewDto
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.exception.ValidationException
import ru.theatrebel.repository.PlayRepository
import ru.theatrebel.repository.ReviewRepository
import ru.theatrebel.service.ReviewServiceImpl
import java.util.*
import kotlin.random.Random

@ExtendWith(MockitoExtension::class)
class ReviewServiceTests {
    @Mock
    lateinit var reviewRepository: ReviewRepository

    @Mock
    lateinit var playRepository: PlayRepository

    @InjectMocks
    lateinit var reviewService: ReviewServiceImpl

    @Test
    fun getReviewById() {
        val id = Random.nextLong()
        val errId = Random.nextLong()

        val playId = Random.nextLong()
        val text = getRandomString()

        `when`(reviewRepository.findById(anyLong())).thenAnswer { invocation ->
            val argument = invocation.getArgument<Long>(0)
            if (argument == id) {
                Optional.of(Review(playId, text, id))
            } else {
                Optional.empty()
            }
        }

        val review = reviewService.getReview(id)

        assertNotNull(review)
        assertEquals(id, review.id)

        assertThrows(NotFoundException::class.java) {
            reviewService.getReview(errId)
        }
    }

    //region saveReviewTests

    @Test
    fun saveReviewSuccess() {
        val playId = Random.nextLong()
        val text = getRandomString()
        val entityId = Random.nextLong()

        `when`(playRepository.existsById(anyLong())).thenAnswer { invocation ->
            val argument = invocation.getArgument<Long>(0)
            argument == playId
        }

        val reviewDto = ReviewDto(playId, text)

        `when`(reviewRepository.save(any())).thenAnswer { invocation ->
            val argument = invocation.getArgument<Review>(0)
            val entity = setId(argument, entityId)

            entity
        }

        val persistedReview = reviewService.postReview(reviewDto)

        assertEquals(entityId, persistedReview.id)
        assertEquals(text, persistedReview.text)
    }

    @Test
    fun saveReviewErrPlayId() {
        val errPlayId = Random.nextLong()
        val text = getRandomString()

        val errPlayIdReviewDto = ReviewDto(errPlayId, text)
        assertThrows(ValidationException::class.java) {
            reviewService.postReview(errPlayIdReviewDto)
        }
    }

    @Test
    fun saveReviewNullPlayId() {
        val text = getRandomString()
        val nullPlayIdReviewDto = ReviewDto(null, text)

        assertThrows(ValidationException::class.java) {
            reviewService.postReview(nullPlayIdReviewDto)
        }
    }

    @Test
    fun saveReviewNullText() {
        val playId = Random.nextLong()
        val nullTextReviewDto = ReviewDto(playId, null)

        assertThrows(ValidationException::class.java) {
            reviewService.postReview(nullTextReviewDto)
        }
    }

    //endregion

    @Test
    fun deleteReview() {
        val id = Random.nextLong()
        val errId = Random.nextLong()

        `when`(reviewRepository.existsById(anyLong())).thenAnswer { invocation ->
            val argument = invocation.getArgument<Long>(0)
            argument == id
        }

        assertThrows(NotFoundException::class.java) {
            reviewService.deleteReview(errId)
        }

        val result = reviewService.deleteReview(id)

        assertEquals(result.status, HttpStatus.OK.value())
        assertEquals(result.message, "Deleted")
    }



    private fun setId(review: Review, id: Long): Review {
        review.id = id
        return review
    }
}