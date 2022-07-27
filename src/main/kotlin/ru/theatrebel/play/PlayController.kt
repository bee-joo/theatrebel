package ru.theatrebel.play

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.theatrebel.writer.WriterRepository

@RestController
@RequestMapping("/api/plays")
class PlayController(private val playRepository: PlayRepository,
                     private val writerRepository: WriterRepository) {

    @GetMapping("/{id}")
    fun getPlay(@PathVariable id: Long) = playRepository.findById(id)

    //@PostMapping
    fun postPlay(@RequestBody play: Play): Play {
        val writers = writerRepository
            .findAllById(play.writers.map {
                it.id
            })
            .toMutableSet()

        play.writers = writers
        writers.forEach { it.plays.add(play) }

        return playRepository.save(play)
    }

    //@PostMapping
    fun postPlayTwo(@RequestBody play: Play): Play {
        play.writers.forEach { writerRepository.findById(it.id!!) }
        return playRepository.save(play)
    }

    @PostMapping
    fun postPlayThree(@RequestBody play: Play) = playRepository.save(play)
}