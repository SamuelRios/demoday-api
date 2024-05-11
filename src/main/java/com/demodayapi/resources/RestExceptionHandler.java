package com.demodayapi.resources;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.demodayapi.exceptions.AccEvalCriteriaNameCanNotBeNullException;
import com.demodayapi.exceptions.AreadyExistInProgressDemodayException;
import com.demodayapi.exceptions.TherIsNotActiveDemodayException;
import com.demodayapi.exceptions.UserCPFAlreadyExistsException;
import com.demodayapi.exceptions.UserEmailAlreadyExistsException;
import com.demodayapi.exceptions.UserIsNotAdminException;
import com.demodayapi.exceptions.UserNotLoggedException;
import com.demodayapi.exceptions.ValidateBiggestBetweenInitEndException;
import com.demodayapi.exceptions.ThereIsNotPeriodOfSubmissionException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidationExceptions(MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        StandardError err = new StandardError();
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.BAD_REQUEST.value());
        err.setErrors(errors);
        err.setMessage("Argumentos Inválidos.");
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<StandardError> handleUserEmaillExistsExceptions(UserEmailAlreadyExistsException exception,
            HttpServletRequest request) {
        StandardError err = new StandardError();
        Map<String, String> errors = new HashMap<>();
        errors.put("email", exception.getMessage());
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.CONFLICT.value());
        err.setErrors(errors);
        err.setMessage("Email já cadastrado.");
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserCPFAlreadyExistsException.class)
    public ResponseEntity<StandardError> handleUserCPFExistsExceptions(UserCPFAlreadyExistsException exception,
            HttpServletRequest request) {
        StandardError err = new StandardError();
        Map<String, String> errors = new HashMap<>();
        errors.put("cpf", exception.getMessage());
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.CONFLICT.value());
        err.setErrors(errors);
        err.setMessage("CPF já cadastrado.");
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserNotLoggedException.class)
    public ResponseEntity<StandardError> handleUserNotLoggedExceptions(UserNotLoggedException exception,
            HttpServletRequest request) {
        StandardError err = new StandardError();
        Map<String, String> errors = new HashMap<>();
        errors.put("logged", "false");
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.UNAUTHORIZED.value());
        err.setErrors(errors);
        err.setMessage(exception.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidateBiggestBetweenInitEndException.class)
    public ResponseEntity<StandardError> handleValidateBiggestBetweenInitEndExceptions(
            ValidateBiggestBetweenInitEndException exception, HttpServletRequest request) {
        StandardError err = new StandardError();
        Map<String, String> errors = new HashMap<>();
        // errors.put("Date", "false");
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.BAD_REQUEST.value());
        err.setErrors(errors);
        err.setMessage(exception.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AreadyExistInProgressDemodayException.class)
    public ResponseEntity<StandardError> handleAreadyExistInProgressDemodayExceptions(
            AreadyExistInProgressDemodayException exception, HttpServletRequest request) {
        StandardError err = new StandardError();
        Map<String, String> errors = new HashMap<>();
        // errors.put("Date", "false");
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.CONFLICT.value());
        err.setErrors(errors);
        err.setMessage(exception.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserIsNotAdminException.class)
    public ResponseEntity<StandardError> handleUserIsNotAdminExceptions(UserIsNotAdminException exception,
            HttpServletRequest request) {
        StandardError err = new StandardError();
        Map<String, String> errors = new HashMap<>();
        // errors.put("Date", "false");
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.UNAUTHORIZED.value());
        err.setErrors(errors);
        err.setMessage(exception.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ThereIsNotPeriodOfSubmissionException.class)
    public ResponseEntity<StandardError> ThereIsNotPeriodOfSubmissionExceptions(
            ThereIsNotPeriodOfSubmissionException exception, HttpServletRequest request) {
        StandardError err = new StandardError();
        Map<String, String> errors = new HashMap<>();
        // errors.put("Date", "false");
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.UNAUTHORIZED.value());
        err.setErrors(errors);
        err.setMessage(exception.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TherIsNotActiveDemodayException.class)
    public ResponseEntity<StandardError> TherIsNotActiveDemodayExceptions(TherIsNotActiveDemodayException exception,
            HttpServletRequest request) {
        StandardError err = new StandardError();
        Map<String, String> errors = new HashMap<>();
        // errors.put("Date", "false");
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.UNAUTHORIZED.value());
        err.setErrors(errors);
        err.setMessage(exception.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccEvalCriteriaNameCanNotBeNullException.class)
    public ResponseEntity<StandardError> AccEvalCriteriaNameCanNotBeNullExceptions(
            AccEvalCriteriaNameCanNotBeNullException exception, HttpServletRequest request) {
        StandardError err = new StandardError();
        Map<String, String> errors = new HashMap<>();
        // errors.put("Date", "false");
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.UNAUTHORIZED.value());
        err.setErrors(errors);
        err.setMessage(exception.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }
}