package com.ou.springdemo.exception;

//Không tìm thấy resource nào đó, ví dụ: user, product, order... trả về 404
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, Object id) {
        super(resource + "không tồn tại với id: " + id);
    }
}
