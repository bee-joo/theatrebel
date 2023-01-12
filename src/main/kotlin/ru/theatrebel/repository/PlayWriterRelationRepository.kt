package ru.theatrebel.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import ru.theatrebel.entity.Play
import ru.theatrebel.entity.PlayWriterRelation
import ru.theatrebel.entity.Writer
import java.util.*

interface PlayWriterRelationRepository : CrudRepository<PlayWriterRelation, UUID> {

    @Query(value = "SELECT w FROM Writer w, PlayWriterRelation pw WHERE pw.playId = ?1 AND w.id = pw.writerId")
    fun getAllWritersByPlayId(playId: Long): List<Writer>

    @Query(value = "SELECT w FROM Writer w, PlayWriterRelation pw WHERE pw.playId = ?1 AND w.id = pw.writerId")
    fun getAllWritersByPlayId(playId: Long, pageable: Pageable): Page<Writer>

    @Query(value = "SELECT p from Play p, PlayWriterRelation pw WHERE pw.writerId = ?1 AND p.id = pw.playId")
    fun getAllPlaysByWriterId(writerId: Long): List<Play>

    @Query(value = "SELECT p from Play p, PlayWriterRelation pw WHERE pw.writerId = ?1 AND p.id = pw.playId")
    fun getAllPlaysByWriterId(writerId: Long, pageable: Pageable): Page<Play>
}