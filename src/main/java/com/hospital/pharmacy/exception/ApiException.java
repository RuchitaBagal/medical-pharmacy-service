package com.hospital.pharmacy.exception;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
