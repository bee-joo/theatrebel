package ru.theatrebel.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.theatrebel.dto.PlayDto
import ru.theatrebel.dto.ReviewDto
import ru.theatrebel.service.PlayService

@RestController
@RequestMapping("/api/plays")
class PlayController(private val playService: PlayService) {

    @GetMapping
    fun getAllPlays() = playService.getAllPlays()

    @GetMapping("/{id}")
    fun getPlay(@PathVariable id: Long) = playService.getPlay(id)

    @GetMapping("/{id}/writers")
    fun getPlayWriters(@PathVariable id: Long) = playService.getWriters(id)

    @GetMapping("/{id}/reviews")
    fun getPlayReviews(@PathVariable id: Long) = playService.getReviews(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postPlay(@RequestBody playDto: PlayDto) = playService.addPlay(playDto)

    @PostMapping("/{id}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    fun postReview(@PathVariable id: Long, @RequestBody reviewDto: ReviewDto) = playService.addReview(id, reviewDto)

    @PatchMapping("/{id}")
    fun patchPlay(@PathVariable id: Long, @RequestBody playDto: PlayDto) = playService.editPlay(id, playDto)

    @DeleteMapping("/{id}")
    fun deletePlay(@PathVariable id: Long) = playService.deletePlay(id)
}