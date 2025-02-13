package com.kt.demo.user.dto.request;

import com.kt.demo.user.domain.User;

public record UserEnrollRequest(
        String email,
        String name,
        String password,
        String phoneNumber
) {
    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();
    }
}