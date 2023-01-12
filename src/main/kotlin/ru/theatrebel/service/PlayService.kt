package ru.theatrebel.service

import org.springframework.data.domain.Page
import ru.theatrebel.dto.PlayDto
import ru.theatrebel.dto.ReviewDto
import ru.theatrebel.dto.request.GetAllPlaysRequest
import ru.theatrebel.entity.*
import ru.theatrebel.dto.view.PlayView
import ru.theatrebel.dto.view.ResponseObject
import ru.theatrebel.dto.view.WriterView

interface PlayService {
    fun addPlay(playDto: PlayDto): Play
    fun getPlay(id: Long): PlayView
    fun getAllPlays(request: GetAllPlaysRequest): Page<PlayView>
    fun getWriters(id: Long, orderBy: String, page: String, count: String): Page<WriterView>
    fun editPlay(id: Long, playDto: PlayDto): Play
    fun deletePlay(id: Long): ResponseObject<String>
    fun addReview(id: Long, reviewDto: ReviewDto): Review
    fun getReviews(id: Long): List<Review>
}