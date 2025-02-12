package com.kt.demo.dto;

public record LoginRequest(
        String email,
        String password
) {
}
