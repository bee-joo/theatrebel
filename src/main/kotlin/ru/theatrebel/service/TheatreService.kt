package ru.theatrebel.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.theatrebel.data.ResponseObject
import ru.theatrebel.data.theatre.*
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.repository.TheatreRepository

interface TheatreService {
    fun getTheatre(id: Long): TheatreView
    fun addTheatre(theatreDto: TheatreDto): TheatreView
    fun getAllTheatres(): List<TheatreView>
    fun editTheatre(id: Long, theatreDto: TheatreDto): TheatreView
    fun deleteTheatre(id: Long): ResponseObject<String>
}

@Service
class TheatreServiceImpl(private val theatreRepository: TheatreRepository) : TheatreService {
    override fun getTheatre(id: Long): TheatreView {
        val theatre = theatreRepository.findById(id).orElseThrow {
            NotFoundException("Theatre with id $id not found")
        }

        return theatre.toView()
    }

    override fun addTheatre(theatreDto: TheatreDto): TheatreView {
        val theatre = theatreDto.toEntity()
        return theatreRepository.save(theatre).toView()
    }

    override fun getAllTheatres(): List<TheatreView> {
        return theatreRepository.findAll().map { it.toView() }
    }

    override fun editTheatre(id: Long, theatreDto: TheatreDto): TheatreView {
        val theatre = theatreRepository.findById(id).orElseThrow {
            NotFoundException("Theatre with id $id not found")
        }

        val theatreEntity = theatreRepository.save(theatre.mapFrom(theatreDto))
        return theatreEntity.toView()
    }

    override fun deleteTheatre(id: Long): ResponseObject<String> {
        if (!theatreRepository.existsById(id)) {
            throw NotFoundException("Theatre with id $id not found")
        }

        theatreRepository.deleteById(id)
        return ResponseObject(HttpStatus.OK.value(), "Deleted")
    }
}