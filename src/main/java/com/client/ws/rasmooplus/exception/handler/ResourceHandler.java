package com.client.ws.rasmooplus.exception.handler;

import com.client.ws.rasmooplus.dto.error.ErrorExceptionDTO;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorExceptionDTO> badRequestException(DataIntegrityViolationException e) {
        String message = e.getMessage();

        ErrorExceptionDTO errorExceptionDTO = ErrorExceptionDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(message)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorExceptionDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorExceptionDTO> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> messages = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(objectError -> {
            String field = ((FieldError) objectError).getField();
            String message = objectError.getDefaultMessage();
            messages.put(field, message);
        });

        ErrorExceptionDTO errorExceptionDTO = ErrorExceptionDTO.builder()
                .message(Arrays.toString(messages.entrySet().toArray()))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorExceptionDTO);
    }
}
