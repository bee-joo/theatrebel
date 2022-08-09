package ru.theatrebel.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import ru.theatrebel.entity.PlayWriterRelation
import java.util.UUID

interface PlayWriterRelationRepository : CrudRepository<PlayWriterRelation, UUID> {

    @Query(value = "SELECT pw FROM PlayWriterRelation pw WHERE pw.playId = ?1")
    fun getAllByPlayId(id: Long): Set<PlayWriterRelation>

    @Query(value = "SELECT pw FROM PlayWriterRelation pw WHERE pw.writerId = ?1")
    fun getAllByWriterId(id: Long): Set<PlayWriterRelation>
}