package ru.theatrebel.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.theatrebel.dto.WriterDto
import ru.theatrebel.dto.toEntity
import ru.theatrebel.entity.Writer
import ru.theatrebel.entity.toView
import ru.theatrebel.repository.PlayRepository
import ru.theatrebel.repository.PlayWriterRelationRepository
import ru.theatrebel.repository.WriterRepository
import ru.theatrebel.view.WriterView

@Service
class WriterService(private val writerRepository: WriterRepository,
                    private val playRepository: PlayRepository,
                    private val playWriterRelationRepository: PlayWriterRelationRepository) {

    fun addWriter(writerDto: WriterDto): Writer = writerRepository.save(writerDto.toEntity())

    fun getWriterById(id: Long): ResponseEntity<WriterView> {
        val writer = writerRepository.findById(id)

        if (writer.isPresent) {
            val writerView = writer.get().toView()
            val playSet = playWriterRelationRepository
                .getAllByWriterId(id)
                .map { it.playId }

            writerView.plays.addAll(playRepository.findAllById(playSet))

            return ResponseEntity(writerView, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }
}