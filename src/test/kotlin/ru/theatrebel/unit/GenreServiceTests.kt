package ru.theatrebel.unit

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import ru.theatrebel.data.genre.Genre
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.repository.GenreRepository
import ru.theatrebel.service.GenreServiceImpl
import java.util.*
import kotlin.random.Random

@ExtendWith(MockitoExtension::class)
class GenreServiceTests {
    @Mock
    lateinit var genreRepository: GenreRepository

    @InjectMocks
    lateinit var genreService: GenreServiceImpl

    @Test
    fun saveGenre() {
        val name = getRandomGenreName()
        val id = Random.nextInt()
        val genre = Genre(name)

        `when`(genreRepository.save(any()))
            .thenReturn(setId(genre, id))

        val persistedGenre = genreService.addGenre(genre)
        assertEquals(genre.name, persistedGenre.name)
        assertEquals(id, persistedGenre.id)
    }

    @Test
    fun getGenreById() {
        val id = Random.nextInt()
        val name = getRandomGenreName()

        `when`(genreRepository.findById(anyInt()))
            .thenReturn(
                Optional.of(Genre(name, id))
            )

        val genre = genreService.getGenre(id)

        assertNotNull(genre)
        assertEquals(id, genre.id)
    }

    @Test
    fun editGenre() {
        val oldName = getRandomGenreName()
        val id = Random.nextInt()

        `when`(genreRepository.findById(anyInt()))
            .thenReturn(
                Optional.of(Genre(oldName, id))
            )

        val newName = getRandomGenreName()

        `when`(genreRepository.save(any()))
            .thenReturn(
                Genre(newName, id)
            )

        val oldGenre = genreService.getGenre(id)
        assertEquals(oldName, oldGenre.name)

        val genre = genreService.editGenre(id, newName)
        assertEquals(newName, genre.name)
    }

    @Test
    fun deleteGenre() {
        val id = Random.nextInt()
        val errId = Random.nextInt()

        `when`(genreRepository.existsById(anyInt())).thenAnswer { invocation ->
            val argument = invocation.getArgument<Int>(0)
            argument == id
        }

        assertThrows(NotFoundException::class.java) {
            genreService.deleteGenre(errId)
        }

        val result = genreService.deleteGenre(id)

        assertEquals(result.status, HttpStatus.OK.value())
        assertEquals(result.message, "Deleted")
    }

    private fun getRandomGenreName(): String {
        val genres = listOf("Драма", "Комедия", "Трагедия")
        return genres.random()
    }

    private fun setId(genre: Genre, id: Int): Genre {
        genre.id = id
        return genre
    }
}