package com.brennfoerder.urlshortener.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleInvalidObjectIdException(exception: InvalidObjectIdException): ResponseEntity<ErrorMessageModel> {
        return ResponseEntity(ErrorMessageModel(HttpStatus.BAD_REQUEST.value(), exception.message), HttpStatus.BAD_REQUEST)
    }
}
