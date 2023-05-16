package ru.theatrebel.data.review

import ru.theatrebel.data.play.Play
import ru.theatrebel.exception.ValidationException

data class ReviewDto(
    var playId: Long? = null,
    val text: String? = null
)

fun ReviewDto.toEntity() : Review {
    val review = Review()
    review.play = Play(playId ?: throw ValidationException("Id is required"))
    review.text = text ?: throw ValidationException("Body is required")

    return review
}
