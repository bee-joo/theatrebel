package ru.theatrebel.writer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ru.theatrebel.play.Play
import javax.persistence.*

@Entity
@Table(name = "writers")
@JsonIgnoreProperties(ignoreUnknown = true)
class Writer {
    @Column(nullable = false)
    var name: String = ""

    @JsonIgnoreProperties("writers", "reviews")
    @ManyToMany(
        mappedBy = "writers",
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.LAZY
    )
    var plays: MutableSet<Play> = mutableSetOf()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
}
