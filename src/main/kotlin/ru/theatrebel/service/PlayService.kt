package ru.theatrebel.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.theatrebel.dto.PlayDto
import ru.theatrebel.dto.toEntity
import ru.theatrebel.entity.Play
import ru.theatrebel.entity.PlayWriterRelation
import ru.theatrebel.entity.toView
import ru.theatrebel.entity.update
import ru.theatrebel.repository.PlayRepository
import ru.theatrebel.repository.PlayWriterRelationRepository
import ru.theatrebel.repository.WriterRepository
import ru.theatrebel.view.PlayView

@Service
class PlayService(private val playRepository: PlayRepository,
                  private val writerRepository: WriterRepository,
                  private val playWriterRelationRepository: PlayWriterRelationRepository) {

    fun addPlay(playDto: PlayDto): ResponseEntity<Play> {
        if (playDto.writerIds == null || !playDto.writerIds.all { writerRepository.existsById(it) }) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        val play = playRepository.save(playDto.toEntity())

        playWriterRelationRepository.saveAll(
            playDto.writerIds.map { PlayWriterRelation(play.id!!, it) }
        )

        return ResponseEntity(play, HttpStatus.OK)
    }

    fun getPlay(id: Long): ResponseEntity<PlayView> {
        val play = playRepository.findById(id)

        if (play.isPresent) {
            val playView = play.get().toView()
            val writerSet = playWriterRelationRepository.getAllByPlayId(id).map { it.writerId }

            playView.writers.addAll(writerRepository.findAllById(writerSet))

            return ResponseEntity(playView, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    fun editPlay(id: Long, playDto: PlayDto): Play {
        val play = playRepository.getReferenceById(id)
        return playRepository.save(play.update(playDto))
    }
}