package ru.theatrebel.writer

import org.springframework.web.bind.annotation.*
import ru.theatrebel.play.PlayRepository

@RestController
@RequestMapping("/api/writers")
class WriterController(private val writerRepository: WriterRepository,
                       private val playRepository: PlayRepository) {

    @GetMapping("/{id}")
    fun getWriterById(@PathVariable id: Long) = writerRepository.findById(id)

    @GetMapping("/{id}/plays")
    fun getWritersPlays(@PathVariable id: Long) = writerRepository.findById(id).get().plays

    @PostMapping
    fun postWriter(@RequestBody writer: Writer) = writerRepository.save(writer)

}