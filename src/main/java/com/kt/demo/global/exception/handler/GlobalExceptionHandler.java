package com.kt.demo.global.exception.handler;

import com.kt.demo.global.exception.FileConvertFailException;
import com.kt.demo.global.exception.errorcode.ErrorCode;
import com.kt.demo.global.exception.errorcode.GlobalErrorCode;
import com.kt.demo.global.exception.response.ErrorResponse;
import com.kt.demo.global.exception.response.ErrorResponse.ValidationError;
import com.kt.demo.global.exception.response.ErrorResponse.ValidationErrors;
import com.kt.demo.user.exception.UserNotFoundException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(FileConvertFailException.class)
    public ResponseEntity<Object> handleFileConvertFail(FileConvertFailException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    /**
     * DTO @Valid 관련 exception 처리
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException e,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        return handleExceptionInternal(e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument() {
        return handleExceptionInternal(GlobalErrorCode.INVALID_PARAMETER);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException() {
        return handleExceptionInternal(GlobalErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .isSuccess(false)
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .results(new ValidationErrors(null))
                .build();
    }

    // DTO @Valid 관련 exception 처리
    private ResponseEntity<Object> handleExceptionInternal(BindException e) {
        return ResponseEntity.status(GlobalErrorCode.INVALID_PARAMETER.getHttpStatus())
                .body(makeErrorResponse(e));
    }

    private ErrorResponse makeErrorResponse(BindException e) {
        final List<ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationError::from)
                .toList();

        return ErrorResponse.builder()
                .isSuccess(false)
                .code(GlobalErrorCode.INVALID_PARAMETER.name())
                .message(GlobalErrorCode.INVALID_PARAMETER.getMessage())
                .results(new ValidationErrors(validationErrorList))
                .build();
    }
}
