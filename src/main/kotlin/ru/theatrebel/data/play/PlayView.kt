package ru.theatrebel.data.play

import com.fasterxml.jackson.annotation.JsonInclude
import ru.theatrebel.data.writer.WriterView
import ru.theatrebel.data.genre.Genre
import ru.theatrebel.data.review.Review

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class PlayView(
    val id: Long,
    val name: String,
    val genre: Genre? = null,
    var writers: List<WriterView> = emptyList(),
    var origname: String? = null,
    var date: Int? = null,
    var description: String? = null,
    var reviews: List<Review> = emptyList(),
    var city: String? = null,
    var text: String? = null,
    var hasText: Boolean = false,
    var image: String? = null
)