package com.gallery.commn.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gallery.commn.ApiResponse;
import com.gallery.commn.ErrorCode;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException customException) {
        ErrorCode errorCode = customException.getErrorCode();

        return ResponseEntity
                .status(errorCode.geHttpStatus())
                .body(ApiResponse.fail(errorCode.getMessage()));
    }
}
