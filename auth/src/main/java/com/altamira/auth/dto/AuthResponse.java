package com.altamira.auth.dto;


public record AuthResponse(
        String email,
        String role
){}