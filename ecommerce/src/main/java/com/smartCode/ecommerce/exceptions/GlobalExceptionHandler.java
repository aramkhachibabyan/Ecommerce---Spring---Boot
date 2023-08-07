package com.smartCode.ecommerce.exceptions;

import com.smartCode.ecommerce.util.ApiError;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleValidationException(HttpServletRequest req, ValidationException e) {
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage(), req.getRequestURI());
    }

//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<ApiError> handleRuntimeExceptionException(HttpServletRequest req, RuntimeException e) {
//        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "something went wrong", req.getRequestURI());
//    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiError> handleAuthenticationException(HttpServletRequest req, AuthenticationException e) {
        return buildResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleBadCredentialsException(HttpServletRequest req, BadCredentialsException e) {
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleResourceNotFoundException(HttpServletRequest req, ResourceNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage(), req.getRequestURI());
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
//    public ResponseEntity<ApiError> handleAccessDeniedException(HttpServletRequest req, AccessDeniedException e) {
//        return buildResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), req.getRequestURI());
//    }

    @ExceptionHandler(DuplicationException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleDuplicationException(HttpServletRequest req, DuplicationException e) {
        return buildResponse(HttpStatus.CONFLICT, e.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(VerificationException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiError> handleVerificationException(HttpServletRequest req, VerificationException e) {
        return buildResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), req.getRequestURI());
    }

    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String message, String requestURI) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", message);
        ApiError apiError = new ApiError(status.value(), requestURI, errors);
        return ResponseEntity.status(status).body(apiError);
    }
}
