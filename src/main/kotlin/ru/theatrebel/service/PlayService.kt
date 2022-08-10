package ru.theatrebel.service

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.theatrebel.dto.PlayDto
import ru.theatrebel.dto.toEntity
import ru.theatrebel.entity.Play
import ru.theatrebel.entity.PlayWriterRelation
import ru.theatrebel.entity.toView
import ru.theatrebel.entity.update
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.exception.ValidateNullException
import ru.theatrebel.repository.PlayRepository
import ru.theatrebel.repository.PlayWriterRelationRepository
import ru.theatrebel.repository.WriterRepository
import ru.theatrebel.view.PlayView
import ru.theatrebel.view.ResponseObject
import ru.theatrebel.view.WriterView

@Service
class PlayService(private val playRepository: PlayRepository,
                  private val writerRepository: WriterRepository,
                  private val playWriterRelationRepository: PlayWriterRelationRepository) {

    fun addPlay(playDto: PlayDto): ResponseObject<Play> {
        if (playDto.writerIds == null || !playDto.writerIds.all { writerRepository.existsById(it) }) {
            throw ValidateNullException("No writers found")
        }

        val play = try {
            playRepository.save(playDto.toEntity())
        } catch (e: ValidateNullException) {
            throw e
        }

        playWriterRelationRepository.saveAll(
            playDto.writerIds.map { PlayWriterRelation(play.id!!, it) }
        )

        return ResponseObject(play, HttpStatus.OK.value())
    }

    fun getPlay(id: Long): ResponseObject<PlayView> {
        val play = playRepository.findById(id)

        if (!play.isPresent) {
            throw NotFoundException("Play with $id not found")
        }

        val playView = play.get().toView()
        val writerSet = playWriterRelationRepository.getAllByPlayId(id).map { it.writerId }

        playView.writers.addAll(writerRepository.findAllById(writerSet))

        return ResponseObject(playView, HttpStatus.OK.value())
    }

    fun getWriters(id: Long): ResponseObject<List<WriterView>> {
        if (!playRepository.existsById(id)) {
            throw IllegalArgumentException("Play with id $id doesn't exist!")
        }

        val writers = playWriterRelationRepository.getAllByPlayId(id).map {
            writerRepository
                .findById(it.writerId)
                .get()
                .toView()
        }

        return ResponseObject(writers, HttpStatus.OK.value())
    }

    fun editPlay(id: Long, playDto: PlayDto): ResponseObject<Play> {
        val play = try {
            playRepository.getReferenceById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw e
        }
        return ResponseObject(playRepository.save(play.update(playDto)), HttpStatus.OK.value())
    }

    fun deletePlay(id: Long): ResponseObject<String> {
        try {
            playRepository.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw e
        }

        return ResponseObject("Deleted successfully!", HttpStatus.OK.value())
    }
}