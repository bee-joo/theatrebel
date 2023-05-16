package ru.theatrebel.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.theatrebel.data.ResponseObject
import ru.theatrebel.data.actor.*
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.repository.ActorRepository

interface ActorService {
    fun getActor(id: Long): ActorView
    fun addActor(actorDto: ActorDto): ActorView
    fun getAllActors(): List<ActorView>
    fun editActor(id: Long, actorDto: ActorDto): ActorView
    fun deleteActor(id: Long): ResponseObject<String>
}

@Service
class ActorServiceImpl(private val actorRepository: ActorRepository) : ActorService {
    override fun getActor(id: Long): ActorView {
        val actor = actorRepository.findById(id).orElseThrow {
            NotFoundException("Actor with id $id not found")
        }

        return actor.toView(withProductions = true, withWriters = true)
    }

    override fun addActor(actorDto: ActorDto): ActorView {
        val actor = actorDto.toEntity()
        return actorRepository.save(actor).toView()
    }

    override fun getAllActors(): List<ActorView> {
        return actorRepository.findAll().map { it.toView() }
    }

    override fun editActor(id: Long, actorDto: ActorDto): ActorView {
        val actor = actorRepository.findById(id).orElseThrow {
            NotFoundException("Actor with id $id not found")
        }

        val actorEntity = actorRepository.save(actor.mapFrom(actorDto))

        return actorEntity.toView()
    }

    override fun deleteActor(id: Long): ResponseObject<String> {
        if (!actorRepository.existsById(id)) {
            throw NotFoundException("Actor with id $id not found")
        }

        actorRepository.deleteById(id)
        return ResponseObject(HttpStatus.OK.value(), "Deleted")
    }
}