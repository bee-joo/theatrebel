package ru.theatrebel.data.play

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import ru.theatrebel.data.genre.Genre
import ru.theatrebel.data.review.Review
import ru.theatrebel.data.writer.Writer
import ru.theatrebel.data.writer.toView
import ru.theatrebel.exception.ValidationException
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "plays")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NamedEntityGraph(
    name = "play_with_all_associations",
    includeAllAttributes = true
)
class Play(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
) {
    @Column(nullable = false)
    var name: String = ""

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    var genre: Genre = Genre()

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "plays_writers",
        joinColumns = [JoinColumn(name = "play_id")],
        inverseJoinColumns = [JoinColumn(name = "writer_id")]
    )
    @JsonIgnoreProperties("plays")
    var writers: MutableSet<Writer> = mutableSetOf()

    @OneToMany(mappedBy = "play", fetch = FetchType.LAZY)
    var reviews: MutableSet<Review> = mutableSetOf()

    var origname: String? = null
    var date: Int? = null

    @Column(length = 2000)
    var description: String? = null

    @Column(length = 1000)
    var city: String? = null

    var text: String? = null
    var hasText: Boolean? = false

    @Column(length = 1000)
    var image: String? = null
}
