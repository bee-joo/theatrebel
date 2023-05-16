package ru.theatrebel.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.theatrebel.data.genre.Genre
import ru.theatrebel.service.GenreService

@RestController
@RequestMapping("/api/genres")
class GenreController(private val genreService: GenreService) {
    @GetMapping
    @CrossOrigin
    fun getAllGenres() = genreService.getAllGenres()

    @GetMapping("/{id}")
    @CrossOrigin
    fun getGenre(@PathVariable id: Int) = genreService.getGenre(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addGenre(@RequestBody genre: Genre) = genreService.addGenre(genre)

    @PutMapping("/{id}")
    fun editGenre(@PathVariable id: Int, @RequestBody name: String) = genreService.editGenre(id, name)

    @DeleteMapping("/{id}")
    fun deleteGenre(@PathVariable id: Int) = genreService.deleteGenre(id)
}