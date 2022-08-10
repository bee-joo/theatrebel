package ru.theatrebel.dto

import ru.theatrebel.entity.Writer
import ru.theatrebel.exception.ValidateNullException

data class WriterDto(
    val name: String? = null,
    val country: String? = null,
    val city: String? = null
)

fun WriterDto.toEntity(): Writer {
    val writer = Writer(this.name ?: throw ValidateNullException("Name is required"))
    this.country?.let { writer.country = it }
    this.city?.let { writer.country = it }

    return writer
}
