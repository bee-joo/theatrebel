package ru.theatrebel.dto.view

import com.fasterxml.jackson.annotation.JsonInclude
import lombok.Builder
import ru.theatrebel.entity.Review

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class PlayView(
    val id: Long,
    val name: String,
    var writers: List<WriterView> = emptyList(),
    var origname: String? = null,
    var date: Int? = null,
    var description: String? = null,
    var reviews: List<Review> = emptyList(),
    var city: String? = null,
    var text: String? = null,
    var hasText: Boolean = false
)