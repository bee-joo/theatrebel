package ru.theatrebel.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.theatrebel.dto.WriterDto
import ru.theatrebel.service.WriterService

@RestController
@RequestMapping("/api/writers")
class WriterController(private val writerService: WriterService) {

    @GetMapping
    fun getAllWriters() = writerService.getAllWriters()

    @GetMapping("/{id}")
    fun getWriterById(@PathVariable id: Long) = writerService.getWriterById(id)

    @GetMapping("/{id}/plays")
    fun getWritersPlays(@PathVariable id: Long) = writerService.getPlaysByWriterId(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postWriter(@RequestBody writerDto: WriterDto) = writerService.addWriter(writerDto)

    @PatchMapping("/{id}")
    fun updateWriter(@PathVariable id: Long, @RequestBody writerDto: WriterDto) = writerService.editWriter(id, writerDto)

    @DeleteMapping("/{id}")
    fun deleteWriter(@PathVariable id: Long) = writerService.deleteWriter(id)

}