package ru.theatrebel.dto

import ru.theatrebel.entity.Review
import ru.theatrebel.exception.ValidationException

data class ReviewDto(
    var playId: Long? = null,
    val text: String? = null
)

fun ReviewDto.toEntity() : Review {
    return Review(
        this.playId ?: throw ValidationException("Id is required"),
        this.text ?: throw ValidationException("Body is required")
    )
}
