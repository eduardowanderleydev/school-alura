package br.com.alura.school.exceptions;

public class UserAlreadyEnrolledInTheCourseException extends RuntimeException {

    public UserAlreadyEnrolledInTheCourseException(String msg) {
        super(msg);
    }
}
