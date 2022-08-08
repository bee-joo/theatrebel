package ru.theatrebel.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import ru.theatrebel.view.WriterView
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

fun Writer.toView(): WriterView {
    val writerView = WriterView(this.id!!, this.name)

    this.country?.let { writerView.country = it }
    this.city?.let { writerView.city = it }

    return writerView
}
