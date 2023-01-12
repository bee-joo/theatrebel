package ru.theatrebel.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "plays")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Play(
    @Column(nullable = false)
    var name: String,

    var origname: String? = null,
    var date: Int? = null,

    @Column(length = 2000)
    var description: String? = null,

    var city: String? = null,
    var text: String? = null,
    @NotNull var hasText: Boolean? = false,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
)
