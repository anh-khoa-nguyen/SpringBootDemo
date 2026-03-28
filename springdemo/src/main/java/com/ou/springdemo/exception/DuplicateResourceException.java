package com.ou.springdemo.exception;


//Tồn tại resource đã tồn tại, ví dụ: user, product, order... trả về 409 conflict
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
