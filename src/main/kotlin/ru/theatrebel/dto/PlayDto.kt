package ru.theatrebel.dto

import ru.theatrebel.entity.Play
import ru.theatrebel.exception.ValidationException

data class PlayDto(
    val name: String? = null,
    val writerIds: List<Long>? = null,
    val origname: String? = null,
    val date: Int? = null,
    val description: String? = null
)

fun PlayDto.toEntity(): Play {
    val play = Play(this.name ?: throw ValidationException("Name excepted"))
    this.origname?.let { play.origname = it }
    this.date?.let { play.date = it }
    this.description?.let { play.description = it }

    return play
}
