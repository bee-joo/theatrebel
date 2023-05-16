package ru.theatrebel.data.director

import com.fasterxml.jackson.annotation.JsonInclude
import ru.theatrebel.data.production.ProductionView

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class DirectorView(
    val id: Long,
    val name: String,
    var photo: String? = null,
    var info: String? = null,
    var productions: List<ProductionView> = listOf()
)
