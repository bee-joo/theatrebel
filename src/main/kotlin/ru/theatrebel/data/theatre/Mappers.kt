package ru.theatrebel.data.theatre

fun Theatre.mapFrom(theatreDto: TheatreDto): Theatre {
    theatreDto.name?.let { this.name = it }
    theatreDto.address?.let { this.address = it }
    theatreDto.photo?.let { this.photo = it }
    theatreDto.description?.let { this.description = it }

    return this
}

fun TheatreDto.toEntity(): Theatre {
    val theatre = Theatre()
    return theatre.mapFrom(this)
}

fun Theatre.toView() = TheatreView(id!!, name, address, description, photo)
