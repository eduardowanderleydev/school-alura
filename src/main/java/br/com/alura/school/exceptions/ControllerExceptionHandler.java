package br.com.alura.school.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError();
        error.setMoment(Instant.now());
        error.setStatus(status.value());
        error.setMessage(e.getMessage());
        error.setError("Resource not found");
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserAlreadyEnrolledInTheCourseException.class)
    protected ResponseEntity<StandardError> userAlreadyEnrolled(UserAlreadyEnrolledInTheCourseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError();
        error.setMoment(Instant.now());
        error.setStatus(status.value());
        error.setMessage(e.getMessage());
        error.setError("User already enrolled on this course");
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(EmptyEnrollmentsException.class)
    protected ResponseEntity<StandardError> emptyEnrollments(EmptyEnrollmentsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        StandardError error = new StandardError();
        error.setMoment(Instant.now());
        error.setStatus(status.value());
        error.setMessage(e.getMessage());
        error.setError("Enrollments list is empty!");
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationError error = new ValidationError();
        error.setMoment(Instant.now());
        error.setStatus(status.value());
        error.setMessage(e.getMessage());
        error.setError("Validation error :");
        error.setPath(request.getRequestURI());
        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            error.add(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(error);
    }

}
