package ru.theatrebel.repository

import org.springframework.data.repository.CrudRepository
import ru.theatrebel.data.actorProductionRelation.ProductionActor
import ru.theatrebel.data.actorProductionRelation.ProductionActorKey

interface ProductionActorRepository : CrudRepository<ProductionActor, ProductionActorKey>