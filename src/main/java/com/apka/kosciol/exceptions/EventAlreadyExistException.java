package com.apka.kosciol.exceptions;

public class EventAlreadyExistException extends Exception {
    public EventAlreadyExistException(String errorMessage) { //, Throwable err
        super(errorMessage);
    }
}
