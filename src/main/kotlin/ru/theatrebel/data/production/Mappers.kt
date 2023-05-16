package ru.theatrebel.data.production

import ru.theatrebel.data.actor.Actor
import ru.theatrebel.data.actor.toView
import ru.theatrebel.data.actorProductionRelation.ProductionActor
import ru.theatrebel.data.director.Director
import ru.theatrebel.data.director.toView
import ru.theatrebel.data.play.Play
import ru.theatrebel.data.play.toView
import ru.theatrebel.data.theatre.Theatre
import ru.theatrebel.data.theatre.toView

fun Production.mapFrom(productionDto: ProductionDto): Production {
    productionDto.description?.let { this.description = it }
    productionDto.playId?.let { this.play = Play(it) }

    productionDto.actorsRoles?.let {
        this.roles = it.map { dto ->
            val actorRole = ProductionActor()
            actorRole.actor = Actor(dto.actorId)
            actorRole.production = this
            dto.role?.let { role -> actorRole.role = role }

            actorRole
        }.toMutableSet()
    }
    productionDto.directorsIds?.let {
        this.directors = it.map { id -> Director(id) }.toMutableSet()
    }
    productionDto.theatresIds?.let {
        this.theatres = it.map { id -> Theatre(id) }.toMutableSet()
    }

    return this
}

fun ProductionDto.toEntity(): Production {
    val production = Production()
    return production.mapFrom(this)
}

fun Production.toView(withPlay: Boolean = false, withWriters: Boolean = false, extraInfo: Boolean = false): ProductionView {
    val productionView = ProductionView(id!!).copy(description = description)

    if (withPlay) {
        productionView.play = play.toView(withWriters = withWriters)
    }

    if (extraInfo) {
        productionView.actors = roles.map { it.actor.toView(role = it.role) }
        productionView.directors = directors.map { it.toView() }
        productionView.theatres = theatres.map { it.toView() }
    }

    return productionView
}