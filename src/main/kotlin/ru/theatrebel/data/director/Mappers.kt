package ru.theatrebel.data.director

import ru.theatrebel.data.production.toView

fun Director.mapFrom(directorDto: DirectorDto): Director {
    directorDto.name?.let { this.name = it }
    directorDto.info?.let { this.info = it }
    directorDto.photo?.let { this.photo = it }

    return this
}

fun DirectorDto.toEntity(): Director {
    val director = Director()
    return director.mapFrom(this)
}

fun Director.toView(withProductions: Boolean = false): DirectorView {
    val directorView = DirectorView(id!!, name).copy(
        photo = photo,
        info = info
    )

    if (withProductions) {
        directorView.productions = this.productions.map { it.toView(withPlay = true) }
    }

    return directorView
}