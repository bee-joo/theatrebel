package ru.theatrebel.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.theatrebel.data.GetAllRequest
import ru.theatrebel.data.ResponseObject
import ru.theatrebel.data.play.*
import ru.theatrebel.data.review.Review
import ru.theatrebel.data.review.ReviewDto
import ru.theatrebel.data.review.toEntity
import ru.theatrebel.data.writer.WriterView
import ru.theatrebel.data.writer.toView
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.exception.ValidationException
import ru.theatrebel.repository.PlayRepository
import ru.theatrebel.repository.ReviewRepository

interface PlayService {
    fun addPlay(playDto: PlayDto): Play
    fun getPlay(id: Long): PlayView
    fun getAllPlays(request: GetAllRequest): Page<PlayView>
    fun getWriters(id: Long, orderBy: String, page: String, count: String): Page<WriterView>
    fun editPlay(id: Long, playDto: PlayDto): Play
    fun deletePlay(id: Long): ResponseObject<String>
    fun addReview(id: Long, reviewDto: ReviewDto): Review
    fun getReviews(id: Long): List<Review>
}

@Service
class PlayServiceImpl(private val playRepository: PlayRepository,
                      private val reviewRepository: ReviewRepository
) : PlayService {

    override fun addPlay(playDto: PlayDto): Play {
        if (playDto.writerIds == null || !playRepository.existsAllByIdIn(playDto.writerIds.toSet())) {
            throw ValidationException("Invalid writers")
        }

        try {
            playDto.validateOnInsert()
        } catch (e: ValidationException) {
            throw e
        }

        return playRepository.save(playDto.toEntity())
    }

    override fun getPlay(id: Long): PlayView {
        val play = playRepository.findById(id).orElseThrow {
            NotFoundException("Play with $id not found")
        }

        return play.toView(withWriters = true, withReviews = true)
    }

    override fun getAllPlays(request: GetAllRequest): Page<PlayView> {
        val page = request.page.toInt()
        val count = request.count.toInt()

        val plays = playRepository.findAllPagination(
            pageable = PageRequest.of(page, count),
            hasText = request.hasText,
            genreId = request.genreId?.toInt(),
            year = request.year?.toInt()
        )

        return plays.map { it.toView(withWriters = true) }
    }

    override fun getWriters(id: Long, orderBy: String, page: String, count: String): Page<WriterView> {
        if (!playRepository.existsById(id)) {
            throw NotFoundException("Play with id $id doesn't exist!")
        }

        val pageRequest = PageRequest.of(page.toInt(), count.toInt(), Sort.by(orderBy))
        val writers = playRepository.findWritersByPlayId(pageRequest, id)

        return writers.map { it.toView() }
    }

    override fun editPlay(id: Long, playDto: PlayDto): Play {
        val play = playRepository.findById(id).orElseThrow {
            throw NotFoundException("Play with id $id doesn't exist!")
        }

        return playRepository.save(play.mapFrom(playDto))
    }

    override fun deletePlay(id: Long): ResponseObject<String> {
        if (!playRepository.existsById(id)) {
            throw NotFoundException("Review with id $id doesn't exist")
        }

        playRepository.deleteById(id)
        return ResponseObject(HttpStatus.OK.value(), "Deleted")
    }

    override fun addReview(id: Long, reviewDto: ReviewDto): Review {
        if (reviewDto.playId == null) {
            reviewDto.playId = id
        }

        if (id != reviewDto.playId || !playRepository.existsById(id)) {
            throw ValidationException("No play found")
        }

        return reviewRepository.save(reviewDto.toEntity())
    }

    override fun getReviews(id: Long): List<Review> {
        if (!playRepository.existsById(id)) {
            throw NotFoundException("Play with id $id doesn't exist!")
        }

        return reviewRepository.findAllByPlayId(id)
    }
}