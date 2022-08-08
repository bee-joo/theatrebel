package ru.theatrebel.entity

import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "plays_writers")
class PlayWriterRelation(
    @Column(name = "play_id")
    var playId: Long,
    @Column(name = "writer_id")
    var writerId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var uuid: UUID? = null
)
