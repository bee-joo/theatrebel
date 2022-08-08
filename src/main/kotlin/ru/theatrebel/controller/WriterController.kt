package ru.theatrebel.controller

import org.springframework.web.bind.annotation.*
import ru.theatrebel.dto.WriterDto
import ru.theatrebel.service.WriterService

@RestController
@RequestMapping("/api/writers")
class WriterController(private val writerService: WriterService) {

    @GetMapping("/{id}")
    fun getWriterById(@PathVariable id: Long) = writerService.getWriterById(id)

    @GetMapping("/{id}/plays")
    fun getWritersPlays(@PathVariable id: Long) = 0

    @PostMapping
    fun postWriter(@RequestBody writerDto: WriterDto) = writerService.addWriter(writerDto)

}