package ru.theatrebel.view

import com.fasterxml.jackson.annotation.JsonInclude
import ru.theatrebel.entity.Play

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WriterView(
    val id: Long,
    val name: String,
    val plays: MutableSet<Play> = mutableSetOf(),
    var country: String? = null,
    var city: String? = null
)
