package ru.theatrebel.data.production

import com.fasterxml.jackson.annotation.JsonInclude
import ru.theatrebel.data.actor.ActorView
import ru.theatrebel.data.director.DirectorView
import ru.theatrebel.data.play.PlayView
import ru.theatrebel.data.theatre.TheatreView

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ProductionView(
    val id: Long,
    var play: PlayView? = null,
    var directors: List<DirectorView> = listOf(),
    var actors: List<ActorView> = listOf(),
    var theatres: List<TheatreView> = listOf(),
    var description: String? = null
)
