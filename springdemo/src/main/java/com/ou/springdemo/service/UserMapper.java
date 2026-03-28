package com.ou.springdemo.service;

import org.springframework.stereotype.Component;

import com.ou.springdemo.dto.UserResponse;
import com.ou.springdemo.entity.User;

@Component
public class UserMapper {
    public UserResponse toResponse(User user) {
        if (user == null) return null;

        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole(),
            user.getFullName(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}
