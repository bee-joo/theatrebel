package ru.theatrebel.data.review

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import ru.theatrebel.data.play.Play
import javax.persistence.*

@Entity
@Table(name = "reviews")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null
) {
    @Column(length = 2000, nullable = false)
    var text: String = ""

    @ManyToOne
    @JoinColumn(name = "play_id", nullable = false)
    var play: Play = Play()
}

fun Review.update(reviewDto: ReviewDto): Review {
    reviewDto.text?.let { this.text = it }

    return this
}