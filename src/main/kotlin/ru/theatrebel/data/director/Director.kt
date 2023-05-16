package ru.theatrebel.data.director

import ru.theatrebel.data.production.Production
import javax.persistence.*

@Entity
@Table(name = "directors")
class Director(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
) {
    var name: String = ""
    var photo: String? = null
    var info: String? = null

    @ManyToMany(mappedBy = "directors", fetch = FetchType.LAZY)
    var productions: MutableList<Production> = mutableListOf()
}