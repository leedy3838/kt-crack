package com.kt.demo.petsitter.exception;

import com.kt.demo.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PetSitterAlreadyExistsException extends RuntimeException {
    private final ErrorCode errorCode;
} 