package ru.theatrebel.play

import org.springframework.stereotype.Service
import ru.theatrebel.writer.WriterRepository

@Service
class PlayService(private val playRepository: PlayRepository,
                  private val writerRepository: WriterRepository)