package ru.theatrebel.data

data class ResponseObject<T>(val status: Int, val message: T)