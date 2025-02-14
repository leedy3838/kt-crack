package com.kt.demo.reservation.exception;

import com.kt.demo.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InvalidReservationTimeException extends RuntimeException {
    private final ErrorCode errorCode;
} 