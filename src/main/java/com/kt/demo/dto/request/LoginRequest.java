package com.kt.demo.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
