package ru.theatrebel.service.impl

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.theatrebel.dto.PlayDto
import ru.theatrebel.dto.ReviewDto
import ru.theatrebel.dto.toEntity
import ru.theatrebel.entity.*
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.exception.ValidationException
import ru.theatrebel.repository.PlayRepository
import ru.theatrebel.repository.PlayWriterRelationRepository
import ru.theatrebel.repository.ReviewRepository
import ru.theatrebel.repository.WriterRepository
import ru.theatrebel.service.PlayService
import ru.theatrebel.view.PlayView
import ru.theatrebel.view.ResponseObject
import ru.theatrebel.view.WriterView

@Service
class PlayServiceImpl(private val playRepository: PlayRepository,
                      private val writerRepository: WriterRepository,
                      private val playWriterRelationRepository: PlayWriterRelationRepository,
                      private val reviewRepository: ReviewRepository) : PlayService {

    override fun addPlay(playDto: PlayDto): Play {
        if (playDto.writerIds == null || !playDto.writerIds.all { writerRepository.existsById(it) }) {
            throw ValidationException("Invalid writers")
        }

        val play = try {
            playRepository.save(playDto.toEntity())
        } catch (e: ValidationException) {
            throw e
        }

        playWriterRelationRepository.saveAll(
            playDto.writerIds.map { PlayWriterRelation(play.id!!, it) }
        )

        return play
    }

    override fun getPlay(id: Long): PlayView {
        val play = playRepository.findById(id).orElseThrow { NotFoundException("Play with $id not found") }
        return play.toView(
            playWriterRelationRepository.getAllWritersByPlayId(id),
            reviewRepository.findAllByPlayId(id)
        )
    }

    override fun getAllPlays(): List<PlayView> {
        return playRepository.findAll()
            .map { play -> play.toView(playWriterRelationRepository.getAllWritersByPlayId(play.id!!)) }
    }

    override fun getWriters(id: Long): List<WriterView> {
        if (!playRepository.existsById(id)) {
            throw NotFoundException("Play with id $id doesn't exist!")
        }

        return playWriterRelationRepository.getAllWritersByPlayId(id).map { it.toView()}
    }

    override fun editPlay(id: Long, playDto: PlayDto): Play {
        val play = playRepository.findById(id).orElseThrow { throw NotFoundException("Play with id $id doesn't exist!") }

        return playRepository.save(play.update(playDto))
    }

    override fun deletePlay(id: Long): ResponseObject<String> {
        try {
            playRepository.deleteById(id)
            return ResponseObject(HttpStatus.OK.value(), "Deleted")
        } catch (e: EmptyResultDataAccessException) {
            throw e
        }
    }

    override fun addReview(id: Long, reviewDto: ReviewDto): Review {
        if (reviewDto.playId == null) {
            reviewDto.playId = id
        }

        if (id != reviewDto.playId || !playRepository.existsById(id)) {
            throw ValidationException("No play found")
        }

        return try {
            reviewRepository.save(reviewDto.toEntity())
        } catch (e: ValidationException) {
            throw e
        }
    }

    override fun getReviews(id: Long): List<Review> {
        if (!playRepository.existsById(id)) throw NotFoundException("Play with id $id doesn't exist!")

        return reviewRepository.findAllByPlayId(id)
    }
}