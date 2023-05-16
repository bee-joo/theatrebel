package ru.theatrebel.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.theatrebel.data.actor.ActorDto
import ru.theatrebel.service.ActorService

@RestController
@RequestMapping("/api/actors")
class ActorController(private val actorService: ActorService) {
    @GetMapping
    @CrossOrigin
    fun getAllActors() = actorService.getAllActors()

    @GetMapping("/{id}")
    @CrossOrigin
    fun getActorById(@PathVariable id: Long) = actorService.getActor(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addActor(@RequestBody actorDto: ActorDto) = actorService.addActor(actorDto)

    @PutMapping("/{id}")
    fun editActor(@PathVariable id: Long, @RequestBody actorDto: ActorDto) = actorService.editActor(id, actorDto)

    @DeleteMapping("/{id}")
    fun deleteActor(@PathVariable id: Long) = actorService.deleteActor(id)
}