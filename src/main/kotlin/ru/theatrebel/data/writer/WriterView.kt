package ru.theatrebel.data.writer

import com.fasterxml.jackson.annotation.JsonInclude
import ru.theatrebel.data.play.PlayView

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class WriterView(
    val id: Long,
    val name: String,
    var plays: List<PlayView> = listOf(),
    var country: String? = null,
    var city: String? = null
)
