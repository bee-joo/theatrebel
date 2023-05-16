package ru.theatrebel.data.writer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import ru.theatrebel.data.play.Play
import javax.persistence.*

@Entity
@Table(name = "writers")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NamedEntityGraph(
    name = "writer_with_all_associations",
    includeAllAttributes = true
)
class Writer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
) {
    @Column(nullable = false)
    var name: String = ""

    var country: String? = null
    var city: String? = null

    @ManyToMany(mappedBy = "writers", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("writers")
    var plays: MutableSet<Play> = mutableSetOf()
}