package dev.poporo.course.ch04.exception;

public class ProblemzAuthenticationException extends RuntimeException {

    public ProblemzAuthenticationException() {
        super("Invalid credential");
    }
}
