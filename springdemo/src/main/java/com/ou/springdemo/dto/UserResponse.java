package com.ou.springdemo.dto;

import java.time.Instant;
import com.ou.springdemo.entity.Role;

public record UserResponse(
    Long id,
    String username,
    String email,
    Role role,
    String fullName,
    Instant createdAt,
    Instant updatedAt){       
}

