package ru.theatrebel.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.theatrebel.data.GetAllRequest
import ru.theatrebel.data.ResponseObject
import ru.theatrebel.data.production.*
import ru.theatrebel.exception.NotFoundException
import ru.theatrebel.repository.ProductionActorRepository
import ru.theatrebel.repository.ProductionRepository

interface ProductionService {
    fun getProduction(id: Long): ProductionView
    fun getAllProductions(request: GetAllRequest): Page<ProductionView>
    fun addProduction(productionDto: ProductionDto): ProductionView
    fun editProduction(id: Long, productionDto: ProductionDto): ProductionView
    fun deleteProduction(id: Long): ResponseObject<String>
}

@Service
class ProductionServiceImpl(private val productionRepository: ProductionRepository,
                        private val productionActorRepository: ProductionActorRepository) : ProductionService {
    override fun getProduction(id: Long): ProductionView {
        val production = productionRepository.findWithAllInfoById(id).orElseThrow {
            NotFoundException("Production with $id not found")
        }

        return production.toView(withPlay = true, withWriters = true, extraInfo = true)
    }

    override fun getAllProductions(request: GetAllRequest): Page<ProductionView> {
        val page = request.page.toInt()
        val count = request.count.toInt()

        val productions = productionRepository.findAllWithDirectorsAndPlaysPagination(
            pageable = PageRequest.of(page, count),
            hasText = request.hasText,
            genreId = request.genreId?.toInt(),
            year = request.year?.toInt()
        )

        return productions.map { it.toView(withPlay = true, withWriters = true) }
    }

    override fun addProduction(productionDto: ProductionDto): ProductionView {
        val production = productionDto.toEntity()
        val entity = productionRepository.save(production)

        productionActorRepository.saveAll(production.roles)

        return entity.toView()
    }

    override fun editProduction(id: Long, productionDto: ProductionDto): ProductionView {
        val production = productionRepository.findById(id).orElseThrow {
            NotFoundException("Production with $id not found")
        }

        val productionEntity = production.mapFrom(productionDto)
        productionActorRepository.saveAll(productionEntity.roles)

        return productionRepository.save(productionEntity).toView()
    }

    override fun deleteProduction(id: Long): ResponseObject<String> {
        if (!productionRepository.existsById(id)) {
            throw NotFoundException("Production with $id not found")
        }

        productionRepository.deleteById(id)
        return ResponseObject(HttpStatus.OK.value(), "Deleted")
    }
}