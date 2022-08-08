package ru.theatrebel.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import ru.theatrebel.entity.PlayWriterRelation
import java.util.UUID

interface PlayWriterRelationRepository : CrudRepository<PlayWriterRelation, UUID> {

    @Query(value = "SELECT * FROM plays_writers WHERE play_id = ?1", nativeQuery = true)
    fun getAllByPlayId(id: Long): Set<PlayWriterRelation>

    fun getAllByWriterId(id: Long): Set<PlayWriterRelation>
}