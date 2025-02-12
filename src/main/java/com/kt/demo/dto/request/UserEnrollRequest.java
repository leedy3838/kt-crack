package com.kt.demo.dto.request;

import com.kt.demo.domain.User;

public record UserEnrollRequest(
        String email,
        String name,
        String password
) {
    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}