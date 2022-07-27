package ru.theatrebel.review

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import ru.theatrebel.play.Play
import javax.persistence.*

@Entity
@Table(name = "reviews")
class Review {
    @ManyToOne
    @JoinColumn(name = "play_id", nullable = false)
    @JsonIgnoreProperties("reviews", "writers")
    lateinit var play: Play

    @Column(length = 2000, nullable = false)
    var text: String = ""

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
}