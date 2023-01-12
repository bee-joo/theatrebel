package ru.theatrebel.service.impl

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.theatrebel.dto.WriterDto
import ru.theatrebel.dto.toEntity
import ru.theatrebel.entity.Writer
import ru.theatrebel.entity.toView
import ru.theatrebel.entity.update
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.exception.ValidationException
import ru.theatrebel.mapper.*
import ru.theatrebel.repository.PlayRepository
import ru.theatrebel.repository.PlayWriterRelationRepository
import ru.theatrebel.repository.WriterRepository
import ru.theatrebel.service.WriterService
import ru.theatrebel.dto.view.PlayView
import ru.theatrebel.dto.view.ResponseObject
import ru.theatrebel.dto.view.WriterView

@Service
class WriterServiceImpl(private val writerRepository: WriterRepository,
                        private val playRepository: PlayRepository,
                        private val playWriterRelationRepository: PlayWriterRelationRepository) : WriterService {

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
        val res = playWriterRelationRepository.getAllPlaysByWriterId(id)

        return if (res.isEmpty())
            throw NotFoundException("Writer with id $id doesn't have plays!")
            else res.map { play ->  play.toView() }
    }

    override fun getWriterById(id: Long): WriterView {
        val writer = writerRepository.findById(id).orElseThrow { NotFoundException("Writer with $id not found") }
        val playList = playWriterRelationRepository.getAllPlaysByWriterId(id)

        return writer.toView(playList)
    }

    override fun editWriter(id: Long, writerDto: WriterDto): Writer {
        val writer = writerRepository.findById(id).orElseThrow { NotFoundException("Writer with $id not found") }

        return writerRepository.save(writer.update(writerDto))
    }

    override fun deleteWriter(id: Long): ResponseObject<String> {
        try {
            writerRepository.deleteById(id)
            return ResponseObject(HttpStatus.OK.value(), "Deleted")
        } catch (e: EmptyResultDataAccessException) {
            throw e
        }
    }
}