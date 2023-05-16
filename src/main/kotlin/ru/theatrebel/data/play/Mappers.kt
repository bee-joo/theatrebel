package ru.theatrebel.data.play

import ru.theatrebel.data.genre.Genre
import ru.theatrebel.data.writer.Writer
import ru.theatrebel.data.writer.toView

fun Play.mapFrom(playDto: PlayDto): Play {
    playDto.name?.let { this.name = it }
    playDto.origname?.let { this.origname = it }
    playDto.date?.let { this.date = it }
    playDto.description?.let { this.description = it }
    playDto.city?.let { this.city = it }
    playDto.text?.let {
        this.text = it
        this.hasText = true
    }
    playDto.genreId?.let { this.genre = Genre(id = it) }
    playDto.image?.let { this.image = it }

    playDto.writerIds?.let {
        this.writers = it.map { id -> Writer(id = id) }.toMutableSet()
    }

    return this
}

fun PlayDto.toEntity(): Play {
    val play = Play()
    return play.mapFrom(this)
}


fun Play.toView(withWriters: Boolean = false, withReviews: Boolean = false): PlayView {

    val playView = PlayView(id!!, name, genre).copy(
        origname = origname,
        date = date,
        description = description,
        text = text,
        hasText = hasText!!,
        image = image
    )

    if (withWriters) {
        playView.writers = writers.map { it.toView() }
    }

    if (withReviews) {
        playView.reviews = reviews.toList()
    }

    return playView
}