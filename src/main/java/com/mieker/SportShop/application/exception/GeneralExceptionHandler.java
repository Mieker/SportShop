package com.mieker.SportShop.application.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

    private static final String ACCESS_DENIED_MESSAGE = "Access denied.";

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(ACCESS_DENIED_MESSAGE, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(ACCESS_DENIED_MESSAGE, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<String> handleNoUserFoundException(NoUserFoundException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(ACCESS_DENIED_MESSAGE, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotSupportedRoleException.class)
    public ResponseEntity<String> handleNotSupportedRoleException(NotSupportedRoleException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(ACCESS_DENIED_MESSAGE, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleInvalidQueryParamException(MissingServletRequestParameterException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidRequestException(HttpMessageNotReadableException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>("Request body malformed.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleInvalidRequestMethodException(HttpRequestMethodNotSupportedException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
