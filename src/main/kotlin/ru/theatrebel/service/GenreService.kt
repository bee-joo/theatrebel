package ru.theatrebel.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.theatrebel.data.ResponseObject
import ru.theatrebel.data.genre.Genre
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.repository.GenreRepository

interface GenreService {
    fun addGenre(genre: Genre): Genre
    fun getGenre(id: Int): Genre
    fun getAllGenres(): List<Genre>
    fun editGenre(id: Int, name: String): Genre
    fun deleteGenre(id: Int): ResponseObject<String>
}

@Service
class GenreServiceImpl(private val genreRepository: GenreRepository) : GenreService {
    override fun addGenre(genre: Genre) = genreRepository.save(genre)

    override fun getGenre(id: Int): Genre {
        val genre = genreRepository.findById(id).orElseThrow {
            NotFoundException("Genre with $id not found")
        }

        return genre
    }

    override fun getAllGenres() = genreRepository.findAll().toList()

    override fun editGenre(id: Int, name: String): Genre {
        val genre = genreRepository.findById(id).orElseThrow {
            NotFoundException("Genre with $id not found")
        }

        genre.name = name
        return genreRepository.save(genre)
    }

    override fun deleteGenre(id: Int): ResponseObject<String> {
        if (!genreRepository.existsById(id)) {
            throw NotFoundException("Genre with id $id doesn't exist")
        }

        genreRepository.deleteById(id)
        return ResponseObject(HttpStatus.OK.value(), "Deleted")
    }
}