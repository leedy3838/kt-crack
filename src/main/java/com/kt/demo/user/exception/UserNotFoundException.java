package com.kt.demo.user.exception;

import com.kt.demo.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
}
