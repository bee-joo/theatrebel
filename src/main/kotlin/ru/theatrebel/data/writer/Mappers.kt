package ru.theatrebel.data.writer

import ru.theatrebel.data.play.toView

fun Writer.toView(withPlays: Boolean = false): WriterView {
    val writerView = WriterView(this.id!!, this.name).copy(
        country = this.country,
        city = this.city
    )

    if (withPlays) {
        writerView.plays = plays.map { it.toView() }
    }

    return writerView
}

fun Writer.mapFrom(writerDto: WriterDto): Writer {
    writerDto.name?.let { this.name = it }
    writerDto.country?.let { this.country = it }
    writerDto.city?.let { this.city = it }

    return this
}

fun WriterDto.toEntity(): Writer {
    val writer = Writer()

    return writer.mapFrom(this)
}