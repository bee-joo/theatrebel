package ru.theatrebel.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.theatrebel.dto.WriterDto
import ru.theatrebel.service.WriterService

@RestController
@RequestMapping("/api/writers")
class WriterController(private val writerService: WriterService) {

    @GetMapping("/{id}")
    fun getWriterById(@PathVariable id: Long) = ResponseEntity.ok().body(writerService.getWriterById(id))

    @GetMapping("/{id}/plays")
    fun getWritersPlays(@PathVariable id: Long) = ResponseEntity.ok().body(writerService.getPlaysByWriterId(id))

    @PostMapping
    fun postWriter(@RequestBody writerDto: WriterDto) = writerService.addWriter(writerDto)

}