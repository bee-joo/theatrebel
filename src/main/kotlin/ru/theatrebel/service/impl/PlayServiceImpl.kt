package ru.theatrebel.service.impl

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.theatrebel.dto.PlayDto
import ru.theatrebel.dto.ReviewDto
import ru.theatrebel.dto.request.GetAllPlaysRequest
import ru.theatrebel.dto.toEntity
import ru.theatrebel.entity.*
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.exception.ValidationException
import ru.theatrebel.repository.PlayRepository
import ru.theatrebel.repository.PlayWriterRelationRepository
import ru.theatrebel.repository.ReviewRepository
import ru.theatrebel.repository.WriterRepository
import ru.theatrebel.service.PlayService
import ru.theatrebel.dto.view.PlayView
import ru.theatrebel.dto.view.ResponseObject
import ru.theatrebel.dto.view.WriterView

@Service
class PlayServiceImpl(private val playRepository: PlayRepository,
                      private val writerRepository: WriterRepository,
                      private val playWriterRelationRepository: PlayWriterRelationRepository,
                      private val reviewRepository: ReviewRepository) : PlayService {

    override fun addPlay(playDto: PlayDto): Play {
        if (playDto.writerIds == null || !playDto.writerIds.all { id -> writerRepository.existsById(id) }) {
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
            writers = playWriterRelationRepository.getAllWritersByPlayId(id),
            reviews = reviewRepository.findAllByPlayId(id)
        )
    }

    override fun getAllPlays(request: GetAllPlaysRequest): Page<PlayView> {
        val matcher = ExampleMatcher.matching()
            .withMatcher("date", ExampleMatcher.GenericPropertyMatcher().exact())
            .withMatcher("hasText", ExampleMatcher.GenericPropertyMatcher().exact())
            .withIgnorePaths("name")
            .withIgnoreNullValues()

        val playMatcher = Play(
            name = "",
            hasText = request.hasText,
            date = request.year?.toInt()
        )

        return playRepository
            .findAll(Example.of(playMatcher, matcher), PageRequest.of(request.page.toInt(), request.count.toInt()))
            .map { play -> play.toView(playWriterRelationRepository.getAllWritersByPlayId(play.id!!)) }
    }

    override fun getWriters(id: Long, orderBy: String, page: String, count: String): Page<WriterView> {
        if (!playRepository.existsById(id)) {
            throw NotFoundException("Play with id $id doesn't exist!")
        }

        return playWriterRelationRepository
            .getAllWritersByPlayId(id, PageRequest.of(page.toInt(), count.toInt(), Sort.by(orderBy)))
            .map { it.toView() }
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