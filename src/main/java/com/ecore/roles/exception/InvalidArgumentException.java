package com.ecore.roles.exception;

import static java.lang.String.format;

public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException() {
        super("Bad Request");
    }
}
