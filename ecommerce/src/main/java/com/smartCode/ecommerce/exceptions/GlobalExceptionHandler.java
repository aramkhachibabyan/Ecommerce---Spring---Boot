package com.smartCode.ecommerce.exceptions;

import com.smartCode.ecommerce.util.ApiError;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleValidationException(HttpServletRequest req, ValidationException e) {
        logError(req, e);
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage(), req.getRequestURI());
    }

//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<ApiError> handleRuntimeExceptionException(HttpServletRequest req, RuntimeException e) {
//        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "something went wrong", req.getRequestURI());
//    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleRuntimeExceptionException(HttpServletRequest req, FeignException e) {
        logError(req, e);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Service is unavailable", req.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        logError(req, e);
        Map<String, String> errors = e.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> isNull(fieldError.getDefaultMessage()) ? "wrong value type" : fieldError.getDefaultMessage()));

        return ResponseEntity.badRequest().body(new ApiError(HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), errors));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiError> handleAuthenticationException(HttpServletRequest req, AuthenticationException e) {
        logError(req, e);
        return buildResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleConstraintViolationException(HttpServletRequest req, ConstraintViolationException e) {
        logError(req, e);
        return buildResponse(HttpStatus.BAD_REQUEST, prettifyMessage(e), req.getRequestURI());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleBadCredentialsException(HttpServletRequest req, BadCredentialsException e) {
        logError(req, e);
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleResourceNotFoundException(HttpServletRequest req, ResourceNotFoundException e) {
        logError(req, e);
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
        logError(req, e);
        return buildResponse(HttpStatus.CONFLICT, e.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(VerificationException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiError> handleVerificationException(HttpServletRequest req, VerificationException e) {
        logError(req, e);
        return buildResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), req.getRequestURI());
    }

    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String message, String requestURI) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", message);
        ApiError apiError = new ApiError(status.value(), requestURI, errors);
        return ResponseEntity.status(status).body(apiError);
    }

    private void logError(HttpServletRequest req, Exception e) {
        log.error(e.getMessage());
        log.error("RequestURI {}", req.getRequestURI());
        e.printStackTrace();
    }

    private String prettifyMessage(Exception e) {
        return nonNull(e.getMessage())
                ? e.getMessage().substring(e.getMessage().indexOf('.') + 1)
                : null;
    }
}
