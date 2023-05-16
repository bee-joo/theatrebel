package ru.theatrebel.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.theatrebel.data.director.DirectorDto
import ru.theatrebel.service.DirectorService

@RestController
@RequestMapping("/api/directors")
class DirectorController(private val directorService: DirectorService) {
    @GetMapping
    @CrossOrigin
    fun getAllDirectors() = directorService.getAllDirectors()

    @GetMapping("/{id}")
    @CrossOrigin
    fun getDirectorById(@PathVariable id: Long) = directorService.getDirector(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addDirector(@RequestBody directorDto: DirectorDto) = directorService.addDirector(directorDto)

    @PutMapping("/{id}")
    fun editDirector(@PathVariable id: Long, @RequestBody directorDto: DirectorDto) = directorService.editDirector(id, directorDto)

    @DeleteMapping("/{id}")
    fun deleteDirector(@PathVariable id: Long) = directorService.deleteDirector(id)
}