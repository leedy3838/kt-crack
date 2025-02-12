package com.kt.demo.dto.response;

import com.kt.demo.domain.User;
import lombok.Builder;

@Builder
public record UserInfoResponse(
        Long id,
        String name
) {

    public static UserInfoResponse fromEntity(User user) {
        return UserInfoResponse.builder()
                .name(user.getName())
                .id(user.getId())
                .build();
    }
}
