package ru.theatrebel.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.theatrebel.dto.PlayDto
import ru.theatrebel.service.PlayService

@RestController
@RequestMapping("/api/plays")
class PlayController(private val playService: PlayService) {

    @GetMapping("/{id}")
    fun getPlay(@PathVariable id: Long) = playService.getPlay(id)

    @PostMapping
    fun postPlayThree(@RequestBody playDto: PlayDto) = playService.addPlay(playDto)

    @PatchMapping("/{id}")
    fun patchPlay(@PathVariable id: Long, @RequestBody playDto: PlayDto) = playService.editPlay(id, playDto)

    @DeleteMapping("/{id}")
    fun deletePlay(@PathVariable id: Long) = playService.deletePlay(id)
}