package ru.theatrebel.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.theatrebel.data.play.PlayView
import ru.theatrebel.data.ResponseObject
import ru.theatrebel.data.play.toView
import ru.theatrebel.data.writer.*
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.exception.ValidationException
import ru.theatrebel.repository.WriterRepository

interface WriterService {
    fun addWriter(writerDto: WriterDto): Writer
    fun getAllWriters(orderBy: String, page: String, count: String): Page<WriterView>
    fun getPlaysByWriterId(id: Long): List<PlayView>
    fun getWriterById(id: Long): WriterView
    fun editWriter(id: Long, writerDto: WriterDto): Writer
    fun deleteWriter(id: Long): ResponseObject<String>
}

@Service
class WriterServiceImpl(private val writerRepository: WriterRepository) : WriterService {

    override fun addWriter(writerDto: WriterDto): Writer {
        return try {
            writerRepository.save(writerDto.toEntity())
        } catch (e: ValidationException) {
            throw e
        }
    }

    override fun getAllWriters(orderBy: String, page: String, count: String): Page<WriterView> {
        return writerRepository
                .findAll(PageRequest.of(page.toInt(), count.toInt(), Sort.by(orderBy)))
                .map { writer -> writer.toView() }
    }

    override fun getPlaysByWriterId(id: Long): List<PlayView> {
        val writer = writerRepository.findById(id).orElseThrow {
            NotFoundException("Writer with $id not found")
        }

        return writer.plays.map { it.toView() }
    }

    override fun getWriterById(id: Long): WriterView {
        val writer = writerRepository.findById(id).orElseThrow {
            NotFoundException("Writer with $id not found")
        }

        return writer.toView(withPlays = true)
    }

    override fun editWriter(id: Long, writerDto: WriterDto): Writer {
        val writer = writerRepository.findById(id).orElseThrow {
            NotFoundException("Writer with $id not found")
        }

        return writerRepository.save(writer.mapFrom(writerDto))
    }

    override fun deleteWriter(id: Long): ResponseObject<String> {
        writerRepository.deleteById(id)
        return ResponseObject(HttpStatus.OK.value(), "Deleted")
    }
}