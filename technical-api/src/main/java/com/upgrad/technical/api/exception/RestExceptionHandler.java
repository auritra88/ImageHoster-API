package com.upgrad.technical.api.exception;

import com.upgrad.technical.api.model.ErrorResponse;
import com.upgrad.technical.service.exception.AuthenticationFailedException;
import com.upgrad.technical.service.exception.ImageNotFoundException;
import com.upgrad.technical.service.exception.UploadFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {


    //Note that the AuthenticationFailedException is mapped to this method to handle the exception
    //Write the annotation which can intercept AuthenticationFailedException in this method
    //Write the annotation here//
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> authenticationFailedException(AuthenticationFailedException exc, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(UploadFailedException.class)
    public ResponseEntity<ErrorResponse> uploadFailedException(UploadFailedException exc, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ErrorResponse> imageNotFoundException(ImageNotFoundException exc, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.NOT_FOUND
        );
    }
}