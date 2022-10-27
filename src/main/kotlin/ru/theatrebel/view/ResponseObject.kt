package ru.theatrebel.view

data class ResponseObject<T>(val status: Int, val message: T)