package com;

public class NoNumber extends RuntimeException{
    public NoNumber() {
        super();
    }

    public NoNumber(String message) {
        super(message);
    }

    public NoNumber(String message, Throwable cause) {
        super(message, cause);
    }
}
