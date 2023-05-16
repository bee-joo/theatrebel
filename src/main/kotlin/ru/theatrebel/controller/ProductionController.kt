package ru.theatrebel.controller

import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.theatrebel.data.GetAllRequest
import ru.theatrebel.data.production.ProductionDto
import ru.theatrebel.data.production.ProductionView
import ru.theatrebel.service.ProductionService

@RestController
@RequestMapping("/api/productions")
class ProductionController(private val productionService: ProductionService) {
    @GetMapping
    @CrossOrigin
    fun getAllProductions(request: GetAllRequest): Page<ProductionView> = productionService.getAllProductions(request)

    @GetMapping("/{id}")
    @CrossOrigin
    fun getProduction(@PathVariable id: Long) = productionService.getProduction(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addProduction(@RequestBody productionDto: ProductionDto) = productionService.addProduction(productionDto)

    @PutMapping("/{id}")
    fun editProduction(@PathVariable id: Long, @RequestBody productionDto: ProductionDto) = productionService.editProduction(id, productionDto)

    @DeleteMapping("/{id}")
    fun deleteProduction(@PathVariable id: Long) = productionService.deleteProduction(id)
}