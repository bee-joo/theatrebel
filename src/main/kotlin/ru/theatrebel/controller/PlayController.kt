package ru.theatrebel.controller

import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.theatrebel.data.play.PlayDto
import ru.theatrebel.data.GetAllRequest
import ru.theatrebel.data.play.PlayView
import ru.theatrebel.data.review.ReviewDto
import ru.theatrebel.data.writer.WriterView
import ru.theatrebel.service.PlayService

@RestController
@RequestMapping("/api/plays")
class PlayController(private val playService: PlayService) {

    @GetMapping
    @CrossOrigin
    fun getAllPlays(request: GetAllRequest): Page<PlayView> = playService.getAllPlays(request)

    @GetMapping("/{id}")
    @CrossOrigin
    fun getPlay(@PathVariable id: Long) = playService.getPlay(id)

    @GetMapping("/{id}/writers")
    @CrossOrigin
    fun getPlayWriters(
        @PathVariable id: Long,
        @RequestParam(name = "orderBy", defaultValue = "name") orderBy: String,
        @RequestParam(name = "page", defaultValue = "0") page: String,
        @RequestParam(name = "count", defaultValue = "20") count: String
    ): Page<WriterView> = playService.getWriters(id, orderBy, page, count)

    @GetMapping("/{id}/reviews")
    @CrossOrigin
    fun getPlayReviews(@PathVariable id: Long) = playService.getReviews(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postPlay(@RequestBody playDto: PlayDto) = playService.addPlay(playDto)

    @PostMapping("/{id}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    fun postReview(@PathVariable id: Long, @RequestBody reviewDto: ReviewDto) = playService.addReview(id, reviewDto)

    @PutMapping("/{id}")
    fun patchPlay(@PathVariable id: Long, @RequestBody playDto: PlayDto) = playService.editPlay(id, playDto)

    @DeleteMapping("/{id}")
    fun deletePlay(@PathVariable id: Long) = playService.deletePlay(id)
}