package ru.theatrebel.service

import ru.theatrebel.dto.PlayDto
import ru.theatrebel.dto.ReviewDto
import ru.theatrebel.entity.*
import ru.theatrebel.view.PlayView
import ru.theatrebel.view.WriterView

interface PlayService {
    fun addPlay(playDto: PlayDto): Play
    fun getPlay(id: Long): PlayView
    fun getWriters(id: Long): List<WriterView>
    fun editPlay(id: Long, playDto: PlayDto): Play
    fun deletePlay(id: Long)
    fun addReview(id: Long, reviewDto: ReviewDto): Review
}