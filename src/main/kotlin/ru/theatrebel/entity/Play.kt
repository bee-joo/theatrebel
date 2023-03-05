package ru.theatrebel.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import lombok.Builder
import ru.theatrebel.dto.PlayDto
import ru.theatrebel.dto.view.PlayView
import ru.theatrebel.exception.ValidationException
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

fun Play.toView(
    writers: List<Writer> = emptyList(),
    reviews: List<Review> = emptyList()
): PlayView {
    val playView = PlayView(this.id!!, this.name).copy(
        origname = this.origname,
        date = this.date,
        description = this.description,
        text = this.text,
        hasText = this.hasText!!
    )

    if (writers.isNotEmpty()) {
        playView.writers = writers.map { writer -> writer.toView() }
    }
    if (reviews.isNotEmpty()) {
        playView.reviews = reviews
    }

    return playView
}

fun Play.update(playDto: PlayDto): Play {
    playDto.name?.let {
        this.name = it
    }
    playDto.origname?.let {
        this.origname = it
    }
    playDto.date?.let {
        this.date = it
    }
    playDto.description?.let {
        this.description = it
    }
    playDto.city?.let {
        this.city = it
    }
    playDto.text?.let {
        this.text = it
        this.hasText = true
    }

    return this
}

fun PlayDto.toEntity(): Play {
    val play = Play(this.name ?: throw ValidationException("Name excepted"))
    return play.update(this)
}