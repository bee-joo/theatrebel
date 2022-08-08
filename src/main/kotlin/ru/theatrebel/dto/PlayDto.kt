package ru.theatrebel.dto

import ru.theatrebel.entity.Play

data class PlayDto(
    val name: String? = null,
    val writerIds: Set<Long>? = null,
    val origname: String? = null,
    val date: Int? = null,
    val description: String? = null
) : Dto

fun PlayDto.toEntity(): Play {
    val play = Play(this.name ?: throw Exception("Null"))
    this.origname?.let { play.origname = it }
    this.date?.let { play.date = it }
    this.description?.let { play.description = it }

    return play
}
