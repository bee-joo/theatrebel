package ru.theatrebel.data.production

import ru.theatrebel.data.actor.Actor
import ru.theatrebel.data.actorProductionRelation.ProductionActor
import ru.theatrebel.data.director.Director
import ru.theatrebel.data.play.Play
import ru.theatrebel.data.theatre.Theatre
import javax.persistence.*

@Entity
@Table(name = "productions")
class Production(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
) {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "productions_directors",
        joinColumns = [JoinColumn(name = "production_id")],
        inverseJoinColumns = [JoinColumn(name = "director_id")]
    )
    var directors: MutableSet<Director> = mutableSetOf()

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "productions_actors",
        joinColumns = [JoinColumn(name = "production_id")],
        inverseJoinColumns = [JoinColumn(name = "actor_id")]
    )
    var actors: MutableSet<Actor> = mutableSetOf()*/
    @OneToMany(mappedBy = "production", fetch = FetchType.LAZY)
    var roles: MutableSet<ProductionActor> = mutableSetOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "play_id", nullable = false)
    var play: Play = Play()

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "productions_theatres",
        joinColumns = [JoinColumn(name = "production_id")],
        inverseJoinColumns = [JoinColumn(name = "theatre_id")]
    )
    var theatres: MutableSet<Theatre> = mutableSetOf()

    var description: String? = null
    var video: String? = null
}