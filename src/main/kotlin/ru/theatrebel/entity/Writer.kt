package ru.theatrebel.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import ru.theatrebel.dto.WriterDto
import ru.theatrebel.dto.view.WriterView
import ru.theatrebel.exception.ValidationException
import javax.persistence.*

@Entity
@Table(name = "writers")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Writer(
    @Column(nullable = false)
    var name: String,

    var country: String? = null,
    var city: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
)

fun Writer.toView(plays: List<Play> = emptyList()): WriterView {
    val writerView = WriterView(this.id!!, this.name).copy(
        country = this.country,
        city = this.city
    )

    if (plays.isNotEmpty()) {
        writerView.plays = plays.map { it.toView() }
    }

    return writerView
}

fun Writer.update(writerDto: WriterDto): Writer {
    writerDto.name?.let { this.name = it }
    writerDto.country?.let { this.country = it }
    writerDto.city?.let { this.city = it }

    return this
}

fun WriterDto.toEntity(): Writer {
    val writer = Writer(this.name ?: throw ValidationException("Name is required"))
    this.country?.let { writer.country = it }
    this.city?.let { writer.country = it }

    return writer
}
