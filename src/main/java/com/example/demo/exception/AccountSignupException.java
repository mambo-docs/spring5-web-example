package com.example.demo.exception;

/**
 * Throw exception during signup processing.
 *
 * @author mambo
 */
public class AccountSignupException extends RuntimeException {

    public AccountSignupException() {
        super("error.signUp");
    }
}
