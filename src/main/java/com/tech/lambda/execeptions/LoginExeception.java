package com.tech.lambda.execeptions;

public class LoginExeception extends RuntimeException {
    public LoginExeception(String message) {
        super(message);
    }
}
