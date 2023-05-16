package ru.theatrebel.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.theatrebel.data.ResponseObject
import ru.theatrebel.data.director.*
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.repository.DirectorRepository

interface DirectorService {
    fun getDirector(id: Long): DirectorView
    fun addDirector(directorDto: DirectorDto): DirectorView
    fun getAllDirectors(): List<DirectorView>
    fun editDirector(id: Long, directorDto: DirectorDto): DirectorView
    fun deleteDirector(id: Long): ResponseObject<String>
}

@Service
class DirectorServiceImpl(private val directorRepository: DirectorRepository) : DirectorService {
    override fun getDirector(id: Long): DirectorView {
        val director = directorRepository.findById(id).orElseThrow {
            NotFoundException("Director with $id not found")
        }

        return director.toView(withProductions = true)
    }

    override fun addDirector(directorDto: DirectorDto): DirectorView {
        val director = directorDto.toEntity()
        return directorRepository.save(director).toView()
    }

    override fun getAllDirectors(): List<DirectorView> {
        return directorRepository.findAll().map {
            it.toView()
        }
    }

    override fun editDirector(id: Long, directorDto: DirectorDto): DirectorView {
        val director = directorRepository.findById(id).orElseThrow {
            NotFoundException("Director with $id not found")
        }

        val directorEntity = directorRepository.save(director.mapFrom(directorDto))

        return directorEntity.toView()
    }

    override fun deleteDirector(id: Long): ResponseObject<String> {
        if (!directorRepository.existsById(id)) {
            throw NotFoundException("Director with $id not found")
        }

        directorRepository.deleteById(id)
        return ResponseObject(HttpStatus.OK.value(), "Deleted")
    }
}