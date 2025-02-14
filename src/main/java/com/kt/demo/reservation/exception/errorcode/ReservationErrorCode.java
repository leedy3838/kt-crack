package com.kt.demo.reservation.exception.errorcode;

import com.kt.demo.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReservationErrorCode implements ErrorCode {
    INVALID_RESERVATION(HttpStatus.NOT_FOUND, "예약 정보를 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}