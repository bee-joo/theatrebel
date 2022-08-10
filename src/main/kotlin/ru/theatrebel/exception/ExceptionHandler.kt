package ru.theatrebel.exception

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.theatrebel.view.ResponseObject

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EmptyResultDataAccessException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleEmptyResultDataAccessException(e: EmptyResultDataAccessException): ResponseObject<String> {
        return ResponseObject("Invalid data: ${e.message}", HttpStatus.BAD_REQUEST.value())
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseObject<String> {
        return ResponseObject("Invalid data: ${e.message}", HttpStatus.BAD_REQUEST.value())
    }

    @ExceptionHandler(ValidateNullException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidateNullException(e: ValidateNullException): ResponseObject<String> {
        return ResponseObject(e.message, HttpStatus.BAD_REQUEST.value())
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(e: NotFoundException): ResponseObject<String> {
        return ResponseObject(e.message, HttpStatus.NOT_FOUND.value())
    }
}