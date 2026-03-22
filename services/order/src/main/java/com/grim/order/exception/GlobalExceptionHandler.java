package com.grim.order.exception;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleProductNotFoundException(BusinessException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }




    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<com.grim.order.dto.ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        var errors=new HashMap<String, String>();
        e.getBindingResult().getFieldErrors()
                .forEach((field)->{
                    var fieldName=((FieldError)field).getField();
                    var errorMessage=field.getDefaultMessage();
                    errors.put(fieldName,errorMessage);
                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new com.grim.order.dto.ErrorResponse(errors));
    }


}
