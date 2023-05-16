package ru.theatrebel.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.theatrebel.data.production.Production
import java.util.Optional

interface ProductionRepository : JpaRepository<Production, Long> {
    @Query(
        value = """
            select pr from Production pr
            join fetch pr.directors
            join fetch pr.play p
            join fetch p.writers
            where 
                (p.hasText = :hasText or :hasText is null) and
                (p.genre.id = :genreId or :genreId is null) and
                (p.date = :year or :year is null)
            order by pr.id asc 
        """,
        countQuery = """
            select count(pr) from Production pr
        """
    )
    fun findAllWithDirectorsAndPlaysPagination(
        pageable: Pageable,
        @Param("hasText") hasText: Boolean? = null,
        @Param("genreId") genreId: Int? = null,
        @Param("year") year: Int? = null
    ): Page<Production>

    @Query(
        value = """
            select pr from Production pr 
            join fetch pr.directors
            join fetch pr.play p
            join fetch p.writers
            join fetch pr.roles r
            join fetch r.actor
            join fetch pr.theatres
            where pr.id = :id
        """)
    fun findWithAllInfoById(@Param("id") id: Long): Optional<Production>
}