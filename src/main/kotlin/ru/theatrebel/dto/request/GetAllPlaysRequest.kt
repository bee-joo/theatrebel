package ru.theatrebel.dto.request

data class GetAllPlaysRequest(
    val page: String = "0",
    val count: String = "20",
    val hasText: Boolean?,
    val type: String?,
    val year: String?
)
