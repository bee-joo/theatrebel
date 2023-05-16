package ru.theatrebel.repository

import org.springframework.data.repository.CrudRepository
import ru.theatrebel.data.genre.Genre

interface GenreRepository : CrudRepository<Genre, Int>