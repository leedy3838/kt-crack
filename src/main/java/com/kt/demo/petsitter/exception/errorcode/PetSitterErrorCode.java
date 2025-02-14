package com.kt.demo.petsitter.exception.errorcode;

import com.kt.demo.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PetSitterErrorCode implements ErrorCode {
    PET_SITTER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 존재하는 펫시터입니다."),
    PET_SITTER_NOT_FOUND(HttpStatus.NOT_FOUND, "펫시터를 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}