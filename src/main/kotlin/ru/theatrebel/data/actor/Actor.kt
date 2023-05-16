package ru.theatrebel.data.actor

import ru.theatrebel.data.actorProductionRelation.ProductionActor
import javax.persistence.*

@Entity
@Table(name = "actors")
class Actor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
) {
    var name: String = ""
    var photo: String? = null

    @OneToMany(mappedBy = "actor", fetch = FetchType.LAZY)
    var roles: MutableSet<ProductionActor> = mutableSetOf()
}