package ru.theatrebel.dto.view

data class ResponseObject<T>(val status: Int, val message: T)