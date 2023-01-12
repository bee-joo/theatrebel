package ru.theatrebel.service

import org.springframework.data.domain.Page
import ru.theatrebel.dto.WriterDto
import ru.theatrebel.entity.Writer
import ru.theatrebel.dto.view.PlayView
import ru.theatrebel.dto.view.ResponseObject
import ru.theatrebel.dto.view.WriterView

interface WriterService {
    fun addWriter(writerDto: WriterDto): Writer
    fun getAllWriters(orderBy: String, page: String, count: String): Page<WriterView>
    fun getPlaysByWriterId(id: Long): List<PlayView>
    fun getWriterById(id: Long): WriterView
    fun editWriter(id: Long, writerDto: WriterDto): Writer
    fun deleteWriter(id: Long): ResponseObject<String>
}