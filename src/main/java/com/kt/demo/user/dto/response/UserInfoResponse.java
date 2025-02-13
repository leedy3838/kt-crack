package com.kt.demo.user.dto.response;

import com.kt.demo.user.domain.User;
import lombok.Builder;

@Builder
public record UserInfoResponse(
        Long id,
        String name,
        String email,
        String phoneNumber
) {

    public static UserInfoResponse fromEntity(User user) {
        return UserInfoResponse.builder()
                .name(user.getName())
                .id(user.getId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
