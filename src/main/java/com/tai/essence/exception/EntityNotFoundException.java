package com.tai.essence.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class EntityNotFoundException extends Exception{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
