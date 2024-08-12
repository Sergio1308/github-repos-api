package com.company.githubReposApi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ErrorResponse(status.value(), ex.getMessage()), status);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(WebClientResponseException ex) {
        String message;
        int statusCode;

        if (ex instanceof WebClientResponseException.Unauthorized) {
            message = "Provided GitHub access token is not valid or expired.";
            statusCode = HttpStatus.UNAUTHORIZED.value();
        } else if (ex instanceof WebClientResponseException.Forbidden) {
            message = "Unauthenticated requests have exceeded the rate limit. "
                    + "Please authenticate using your GitHub personal access token.";
            statusCode = HttpStatus.FORBIDDEN.value();
        } else {
            message = ex.getMessage();
            statusCode = ex.getStatusCode().value();
        }

        return new ResponseEntity<>(
                new ErrorResponse(statusCode, message), HttpStatus.valueOf(statusCode)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalError(Exception ex) {
        log.error("Internal server error: ", ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorResponse(status.value(), ex.getMessage()), status);
    }
}
