package ru.theatrebel.data.theatre

import javax.persistence.*

@Entity
@Table(name = "theatres")
class Theatre(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
) {
    var name: String = ""
    var address: String = ""
    var description: String? = null
    var photo: String? = null
}