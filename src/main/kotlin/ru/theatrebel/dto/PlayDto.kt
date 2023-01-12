package ru.theatrebel.dto

data class PlayDto(
    val name: String? = null,
    val writerIds: List<Long>? = null,
    val origname: String? = null,
    val date: Int? = null,
    val description: String? = null,
    val city: String? = null,
    val text: String? = null
)
