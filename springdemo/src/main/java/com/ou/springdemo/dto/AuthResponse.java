package com.ou.springdemo.dto;

/*
*  Reponse sau đăng nhập / refresh
*/

public record AuthResponse (
    String accessToken,
    String refreshToken,
    String tokenType,
    Long expiresInSeconds,
    UserResponse user
){
    public AuthResponse{
        if (tokenType == null || tokenType.isBlank()) {
            tokenType = "Bearer";
        }
    }

    public static AuthResponse of(
        String accessToken,
        String refreshToken,
        Long expiresInSeconds,
        UserResponse user
    ) {
        return new AuthResponse(accessToken, refreshToken, "Bearer", expiresInSeconds, user);
    }
}
