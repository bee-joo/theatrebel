package ru.theatrebel.service.impl

import org.springframework.stereotype.Service
import ru.theatrebel.dto.WriterDto
import ru.theatrebel.dto.toEntity
import ru.theatrebel.entity.Writer
import ru.theatrebel.entity.toView
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.exception.ValidationException
import ru.theatrebel.repository.PlayWriterRelationRepository
import ru.theatrebel.repository.WriterRepository
import ru.theatrebel.service.WriterService
import ru.theatrebel.view.PlayView
import ru.theatrebel.view.WriterView

@Service
class WriterServiceImpl(private val writerRepository: WriterRepository,
                        private val playWriterRelationRepository: PlayWriterRelationRepository) : WriterService {

    override fun addWriter(writerDto: WriterDto): Writer {
        val writer = try {
            writerRepository.save(writerDto.toEntity())
        } catch (e: ValidationException) {
            throw e
        }

        return writer
    }

    override fun getPlaysByWriterId(id: Long): List<PlayView> {
        return playWriterRelationRepository.getAllByWriterId(id).map { it.toView() }
    }

    override fun getWriterById(id: Long): WriterView {
        val writer = writerRepository.findById(id).orElseThrow { NotFoundException("Writer with $id not found") }

        val writerView = writer.toView()
        val playList = playWriterRelationRepository.getAllByWriterId(id)

        writerView.plays.addAll(playList)

        return writerView
    }
}