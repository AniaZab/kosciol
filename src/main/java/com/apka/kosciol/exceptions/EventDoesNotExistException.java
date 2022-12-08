package com.apka.kosciol.exceptions;

public class EventDoesNotExistException extends Exception {
    public EventDoesNotExistException(String errorMessage) { //, Throwable err
        super(errorMessage);
    }
}
