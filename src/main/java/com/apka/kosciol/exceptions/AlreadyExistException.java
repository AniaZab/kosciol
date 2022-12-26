package com.apka.kosciol.exceptions;

public class AlreadyExistException extends Exception {
    public AlreadyExistException(String errorMessage) { //, Throwable err
        super(errorMessage);
    }
}
