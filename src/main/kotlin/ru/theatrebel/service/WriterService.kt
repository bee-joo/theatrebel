package ru.theatrebel.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.theatrebel.dto.WriterDto
import ru.theatrebel.dto.toEntity
import ru.theatrebel.entity.Writer
import ru.theatrebel.entity.toView
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.exception.ValidateNullException
import ru.theatrebel.repository.PlayRepository
import ru.theatrebel.repository.PlayWriterRelationRepository
import ru.theatrebel.repository.WriterRepository
import ru.theatrebel.view.ResponseObject
import ru.theatrebel.view.WriterView

@Service
class WriterService(private val writerRepository: WriterRepository,
                    private val playRepository: PlayRepository,
                    private val playWriterRelationRepository: PlayWriterRelationRepository) {

    fun addWriter(writerDto: WriterDto): ResponseObject<Writer> {
        val writer = try {
            writerRepository.save(writerDto.toEntity())
        } catch (e: ValidateNullException) {
            throw e
        }

        return ResponseObject(writer, HttpStatus.OK.value())
    }

    fun getWriterById(id: Long): ResponseObject<WriterView> {
        val writer = writerRepository.findById(id)

        if (!writer.isPresent) {
            throw NotFoundException("Writer with $id not found")
        }

        val writerView = writer.get().toView()
        val playSet = playWriterRelationRepository
            .getAllByWriterId(id)
            .map { it.playId }

        writerView.plays.addAll(playRepository.findAllById(playSet))

        return ResponseObject(writerView, HttpStatus.OK.value())
    }
}