package com.grim.ecom.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
public class CustomerNotFoundException extends RuntimeException {

    private String message;


    public CustomerNotFoundException(String format) {
    }
}
