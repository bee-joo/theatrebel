package ru.theatrebel.exception

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.theatrebel.dto.view.ResponseObject

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EmptyResultDataAccessException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleEmptyResultDataAccessException(e: EmptyResultDataAccessException) =
        ResponseEntity
            .badRequest()
            .body(ResponseObject(HttpStatus.BAD_REQUEST.value(), "Invalid data: ${e.message}"))

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: IllegalArgumentException) =
        ResponseEntity
            .badRequest()
            .body(ResponseObject(HttpStatus.BAD_REQUEST.value(), "Invalid data: ${e.message}"))

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidateNullException(e: ValidationException) =
        ResponseEntity
            .badRequest()
            .body(ResponseObject(HttpStatus.BAD_REQUEST.value(), "Invalid data: ${e.message}"))

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(e: NotFoundException) =
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ResponseObject(HttpStatus.NOT_FOUND.value(), "Not found: ${e.message}"))

}