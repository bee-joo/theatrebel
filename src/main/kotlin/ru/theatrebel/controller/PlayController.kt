package ru.theatrebel.controller

import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.theatrebel.dto.PlayDto
import ru.theatrebel.dto.ReviewDto
import ru.theatrebel.dto.request.GetAllPlaysRequest
import ru.theatrebel.dto.view.PlayView
import ru.theatrebel.dto.view.WriterView
import ru.theatrebel.service.PlayService

@RestController
@RequestMapping("/api/plays")
@CrossOrigin
class PlayController(private val playService: PlayService) {

    @GetMapping
    fun getAllPlays(request: GetAllPlaysRequest): Page<PlayView> = playService.getAllPlays(request)

    @GetMapping("/{id}")
    fun getPlay(@PathVariable id: Long) = playService.getPlay(id)

    @GetMapping("/{id}/writers")
    fun getPlayWriters(
        @PathVariable id: Long,
        @RequestParam(name = "orderBy", defaultValue = "name") orderBy: String,
        @RequestParam(name = "page", defaultValue = "0") page: String,
        @RequestParam(name = "count", defaultValue = "20") count: String
    ): Page<WriterView> = playService.getWriters(id, orderBy, page, count)

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