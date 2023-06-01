package com.client.ws.rasmooplus.exception.handler;

import com.client.ws.rasmooplus.dto.error.ErrorExceptionDTO;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResourceHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorExceptionDTO> notFoundException(NotFoundException e) {
        String message = e.getMessage();

        ErrorExceptionDTO errorExceptionDTO = ErrorExceptionDTO.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(message)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorExceptionDTO);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorExceptionDTO> badRequestException(BadRequestException e) {
        String message = e.getMessage();

        ErrorExceptionDTO errorExceptionDTO = ErrorExceptionDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(message)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorExceptionDTO);
    }
}
