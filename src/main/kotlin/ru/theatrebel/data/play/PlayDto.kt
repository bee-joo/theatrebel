package ru.theatrebel.data.play

data class PlayDto(
    val name: String? = null,
    val writerIds: List<Long>? = null,
    val origname: String? = null,
    val date: Int? = null,
    val description: String? = null,
    val city: String? = null,
    val text: String? = null,
    val genreId: Int? = null,
    val image: String? = null
)
