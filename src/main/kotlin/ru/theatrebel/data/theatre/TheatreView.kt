package ru.theatrebel.data.theatre

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class TheatreView(
    val id: Long,
    var name: String,
    var address: String,
    var description: String? = null,
    var photo: String? = null
)
