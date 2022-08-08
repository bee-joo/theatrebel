package ru.theatrebel.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import ru.theatrebel.dto.PlayDto
import ru.theatrebel.view.PlayView
import javax.persistence.*

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
)

fun Play.toView(): PlayView {
    val playView = PlayView(this.id!!, this.name)

    this.origname?.let { playView.origname = it }
    this.date?.let { playView.date = it }
    this.description?.let { playView.description = it }

    return playView
}

fun Play.update(playDto: PlayDto): Play {
    playDto.name?.let { this.name = it }
    playDto.origname?.let { this.origname = it }
    playDto.date?.let { this.date = it }
    playDto.description?.let { this.description = it }

    return this
}
