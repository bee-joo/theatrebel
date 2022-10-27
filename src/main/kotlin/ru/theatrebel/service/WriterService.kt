package ru.theatrebel.service

import ru.theatrebel.dto.WriterDto
import ru.theatrebel.entity.Writer
import ru.theatrebel.view.PlayView
import ru.theatrebel.view.ResponseObject
import ru.theatrebel.view.WriterView

interface WriterService {
    fun addWriter(writerDto: WriterDto): Writer
    fun getAllWriters(): List<WriterView>
    fun getPlaysByWriterId(id: Long): List<PlayView>
    fun getWriterById(id: Long): WriterView
    fun editWriter(id: Long, writerDto: WriterDto): Writer
    fun deleteWriter(id: Long): ResponseObject<String>
}