package ru.theatrebel.view

import com.fasterxml.jackson.annotation.JsonInclude
import ru.theatrebel.entity.Review

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class PlayView(
    val id: Long,
    val name: String,
    var writers: List<WriterView>? = null,
    var origname: String? = null,
    var date: Int? = null,
    var description: String? = null,
    var reviews: List<Review>? = null
)