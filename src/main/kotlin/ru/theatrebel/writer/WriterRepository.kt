package ru.theatrebel.writer

import org.springframework.data.jpa.repository.JpaRepository

interface WriterRepository : JpaRepository<Writer, Long> {
}