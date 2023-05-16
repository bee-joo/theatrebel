package ru.theatrebel.data

import io.swagger.v3.oas.annotations.media.Schema
import org.springdoc.api.annotations.ParameterObject

@ParameterObject
class GetAllRequest(
    @field:Schema(defaultValue = "0")
    val page: String = "0",
    @field:Schema(defaultValue = "20")
    val count: String = "20",
    val hasText: Boolean? = null,
    val genreId: String? = null,
    val year: String? = null
)
