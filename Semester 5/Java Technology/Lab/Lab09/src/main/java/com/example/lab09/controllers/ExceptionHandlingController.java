package com.example.lab09.controllers;

import com.example.lab09.payload.ApiResponse;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlingController implements org.springframework.boot.web.servlet.error.ErrorController{

    @ExceptionHandler({ChangeSetPersister.NotFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity<ApiResponse> handleNotFoundException(Exception ex) {
        ApiResponse response = ApiResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("404 error")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<ApiResponse> handleForbiddenException(Exception ex) {
        ApiResponse response = ApiResponse.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .message("403 error")
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        String errorMessage = ex.getMethod() + " method is not supported for this request";
        ApiResponse response = ApiResponse.builder()
                .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message(errorMessage)
                .build();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(response);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        ApiResponse response = ApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("File upload is too large")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(InternalError.class)
    public ResponseEntity<ApiResponse> handleInternal(Exception ex) {
        ApiResponse response = ApiResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("500 error")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}