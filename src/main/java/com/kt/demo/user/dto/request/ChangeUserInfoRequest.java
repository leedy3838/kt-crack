package com.kt.demo.user.dto.request;

public record ChangeUserInfoRequest(
        String name,
        String phoneNumber
) {
}
