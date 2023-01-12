package ru.theatrebel.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@IdClass(PlayWriterKey::class)
@Table(name = "plays_writers")
class PlayWriterRelation(
    @Id
    @Column(name = "play_id")
    var playId: Long = 0,

    @Id
    @Column(name = "writer_id")
    var writerId: Long = 0
)

class PlayWriterKey(
    var playId: Long = 0,
    var writerId: Long = 0
) : Serializable
