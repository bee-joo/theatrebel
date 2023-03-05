package ru.theatrebel.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import org.springdoc.api.annotations.ParameterObject

@ParameterObject
class GetAllPlaysRequest {
    @Schema(defaultValue = "0")
    val page: String = "0"
    @Schema(defaultValue = "20")
    val count: String = "20"
    val hasText: Boolean? = null
    val type: String? = null
    val year: String? = null
}
