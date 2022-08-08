package ru.theatrebel.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.theatrebel.entity.Writer

interface WriterRepository : JpaRepository<Writer, Long> {
}