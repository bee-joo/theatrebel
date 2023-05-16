package ru.theatrebel.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.theatrebel.data.theatre.TheatreDto
import ru.theatrebel.service.TheatreService

@RestController
@RequestMapping("/api/theatres")
class TheatreController(private val theatreService: TheatreService) {
    @GetMapping
    @CrossOrigin
    fun getAllTheatres() = theatreService.getAllTheatres()

    @GetMapping("/{id}")
    @CrossOrigin
    fun getTheatreById(@PathVariable id: Long) = theatreService.getTheatre(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addTheatre(@RequestBody theatreDto: TheatreDto) = theatreService.addTheatre(theatreDto)

    @PutMapping("/{id}")
    fun editTheatre(@PathVariable id: Long, @RequestBody theatreDto: TheatreDto) = theatreService.editTheatre(id, theatreDto)

    @DeleteMapping("/{id}")
    fun deleteTheatre(@PathVariable id: Long) = theatreService.deleteTheatre(id)
}