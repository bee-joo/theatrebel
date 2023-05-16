package ru.theatrebel.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.theatrebel.data.actor.Actor

interface ActorRepository : JpaRepository<Actor, Long> {
    @Query(
        value = """
            select a from Actor a
            order by a.id asc
        """,
        countQuery = """
            select count(a) from Actor a
        """
    )
    fun findAllPagination(pageable: Pageable): Page<Actor>

    @Query(value = "select a from Actor a")
    override fun findAll(): MutableList<Actor>
}