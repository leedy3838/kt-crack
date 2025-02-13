package com.kt.demo.global.exception;

import com.kt.demo.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileConvertFailException extends RuntimeException {

    private final ErrorCode errorCode;
}
