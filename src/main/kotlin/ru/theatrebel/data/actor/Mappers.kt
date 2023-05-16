package ru.theatrebel.data.actor

import ru.theatrebel.data.production.toView

fun Actor.mapFrom(actorDto: ActorDto): Actor {
    actorDto.name?.let { this.name = it }
    actorDto.photo?.let { this.photo = it }

    return this
}

fun Actor.toView(withProductions: Boolean = false, withWriters: Boolean = false, role: String? = null): ActorView {
    val actorView = ActorView(id!!, name, photo)
        .copy(role = role)

    if (withProductions) {
        actorView.productions = roles.map { it.production.toView(withWriters = withWriters) }
    }

    return actorView
}

fun ActorDto.toEntity(): Actor {
    val actor = Actor()
    return actor.mapFrom(this)
}