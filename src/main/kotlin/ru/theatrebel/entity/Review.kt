package ru.theatrebel.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import ru.theatrebel.dto.ReviewDto
import javax.persistence.*

@Entity
@Table(name = "reviews")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Review(
    @Column(name = "play_id")
    var playId: Long,

    @Column(length = 2000, nullable = false)
    var text: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
)

fun Review.update(reviewDto: ReviewDto): Review {
    reviewDto.text?.let { this.text = it }

    return this
}