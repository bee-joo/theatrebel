package ru.theatrebel.view

import com.fasterxml.jackson.annotation.JsonProperty

data class ResponseObject<T>(
    @JsonProperty val message: T,
    @JsonProperty val status: Int)