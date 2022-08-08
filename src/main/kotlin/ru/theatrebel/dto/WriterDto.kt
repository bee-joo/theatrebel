package ru.theatrebel.dto

import ru.theatrebel.entity.Writer

data class WriterDto(
    val name: String,
    val country: String? = null,
    val city: String? = null
) : Dto

fun WriterDto.toEntity(): Writer {
    val writer = Writer(this.name)
    this.country?.let { writer.country = it }
    this.city?.let { writer.country = it }

    return writer
}
