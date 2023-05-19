package com.ecore.roles.exception;

import static java.lang.String.format;

public class ValidationException extends RuntimeException{

    public <T> ValidationException(Class<T> resource, String reason) {
        super(format("Invalid '%s' object. Reason: '%s'", resource.getSimpleName(), reason));
    }
}
