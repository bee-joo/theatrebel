package ru.theatrebel.data.play

import javax.xml.bind.ValidationException

fun PlayDto.validateOnInsert() {
    when {
        (genreId == null) -> throw ValidationException("Genre id is null")
        (name == null || name == "") -> throw ValidationException("Name is emtpy")
        (writerIds == null || writerIds == emptyList<Long>()) -> throw ValidationException("Writers are emtpy")
    }
}