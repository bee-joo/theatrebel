package ru.theatrebel.play

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import ru.theatrebel.review.Review
import ru.theatrebel.writer.Writer
import javax.persistence.*

@Entity
@Table(name = "plays")
@JsonIgnoreProperties(ignoreUnknown = true)
class Play {
    @Column(nullable = false)
    var name: String = ""

    @JsonIgnoreProperties("plays")
    @ManyToMany(
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "writers_plays",
        joinColumns = [JoinColumn(name = "play_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "writer_id", referencedColumnName = "id")],
    )
    var writers: MutableSet<Writer> = mutableSetOf()

    var origname: String? = null
    var date: Int? = null

    @Column(length = 2000)
    var description: String? = null

    @OneToMany(mappedBy = "play", cascade = [CascadeType.ALL])
    @JsonIgnoreProperties("play")
    var reviews: MutableSet<Review> = mutableSetOf()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
}
