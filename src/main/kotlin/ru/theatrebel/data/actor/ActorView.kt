package ru.theatrebel.data.actor

import com.fasterxml.jackson.annotation.JsonInclude
import ru.theatrebel.data.production.ProductionView

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ActorView(
    val id: Long,
    val name: String,
    var photo: String? = null,
    var productions: List<ProductionView> = emptyList(),
    var role: String? = null
)
