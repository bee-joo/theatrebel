package ru.theatrebel.repository

import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.theatrebel.data.play.Play
import ru.theatrebel.data.writer.Writer

interface PlayRepository : JpaRepository<Play, Long> {
    /*@EntityGraph(value = "play_with_all_associations", type = EntityGraph.EntityGraphType.LOAD)
    override fun findAll(pageable: Pageable): Page<Play>*/

    /*@EntityGraph(value = "play_with_all_associations", type = EntityGraph.EntityGraphType.FETCH)
    override fun <S : Play?> findAll(example: Example<S>, pageable: Pageable): Page<S>*/

    @Query(
        value = """
            select p from Play p
            join fetch p.genre
            join fetch p.writers
            where 
                (p.hasText = :hasText or :hasText is null) and
                (p.genre.id = :genreId or :genreId is null) and
                (p.date = :year or :year is null)
            order by p.id asc
        """,
        countQuery = """
            select count(p) from Play p
        """
    )
    fun findAllPagination(
        pageable: Pageable,
        @Param("hasText") hasText: Boolean? = null,
        @Param("genreId") genreId: Int? = null,
        @Param("year") year: Int? = null
    ): Page<Play>

    @Query(
        value = """
            select p.writers from Play p
            where p.id = :id
        """
    )
    fun findWritersByPlayId(pageable: Pageable, @Param("id") id: Long): Page<Writer>

    fun existsAllByIdIn(ids: Set<Long>): Boolean
}