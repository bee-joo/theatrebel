package ru.theatrebel.view

import com.fasterxml.jackson.annotation.JsonInclude
import ru.theatrebel.entity.Writer

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class PlayView(
    val id: Long,
    val name: String,
    var writers: MutableSet<Writer> = mutableSetOf(),
    var origname: String? = null,
    var date: Int? = null,
    var description: String? = null
)