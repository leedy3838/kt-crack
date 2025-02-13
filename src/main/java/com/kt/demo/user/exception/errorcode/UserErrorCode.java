package com.kt.demo.user.exception.errorcode;

import com.kt.demo.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User is not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}