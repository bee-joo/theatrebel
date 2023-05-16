package ru.theatrebel.data.actorProductionRelation

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class ProductionActorKey(
    @Column(name = "production_id")
    var productionId: Long = 0,

    @Column(name = "actor_id")
    var actorId: Long = 0
) : Serializable
