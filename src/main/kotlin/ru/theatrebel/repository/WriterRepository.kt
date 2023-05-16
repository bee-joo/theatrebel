package ru.theatrebel.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import ru.theatrebel.data.writer.Writer
interface WriterRepository : JpaRepository<Writer, Long> {
    @EntityGraph(value = "writer_with_all_associations", type = EntityGraph.EntityGraphType.FETCH)
    override fun findAll(): List<Writer>
}