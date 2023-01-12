package ru.theatrebel.mapper

import ru.theatrebel.dto.PlayDto
import ru.theatrebel.dto.view.PlayView
import ru.theatrebel.entity.Play
import ru.theatrebel.entity.Review
import ru.theatrebel.entity.Writer
import ru.theatrebel.entity.toView
import ru.theatrebel.exception.ValidationException

fun Play.toView(
    writers: List<Writer> = emptyList(),
    reviews: List<Review> = emptyList()
): PlayView {
    val playView = PlayView(this.id!!, this.name)

    this.origname?.let { playView.origname = it }
    this.date?.let { playView.date = it }
    this.description?.let { playView.description = it }
    this.city?.let { playView.city = it }
    this.text?.let { playView.text = it }
    playView.hasText = this.hasText!!;

    if (writers.isNotEmpty()) {
        playView.writers = writers.map { writer -> writer.toView() }
    }
    if (reviews.isNotEmpty()) {
        playView.reviews = reviews
    }

    return playView
}

fun Play.update(playDto: PlayDto): Play {
    playDto.name?.let { this.name = it }
    playDto.origname?.let { this.origname = it }
    playDto.date?.let { this.date = it }
    playDto.description?.let { this.description = it }
    playDto.city?.let { this.city = it }
    playDto.text?.let {
        this.text = it
        this.hasText = true
    }

    return this
}

fun PlayDto.toEntity(): Play {
    val play = Play(this.name ?: throw ValidationException("Name excepted"))
    return play.update(this)
}