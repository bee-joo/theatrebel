package ru.theatrebel.data.actorProductionRelation

import ru.theatrebel.data.actor.Actor
import ru.theatrebel.data.production.Production
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.Table

@Entity
@Table(name = "productions_actors")
class ProductionActor {
    @EmbeddedId
    var id: ProductionActorKey = ProductionActorKey()

    @ManyToOne
    @MapsId("productionId")
    @JoinColumn(name = "production_id")
    var production: Production = Production()

    @ManyToOne
    @MapsId("actorId")
    @JoinColumn(name = "actor_id")
    var actor: Actor = Actor()

    var role: String = ""
}