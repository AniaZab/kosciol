package com.apka.kosciol.exceptions;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String errorMessage) { //, Throwable err
        super(errorMessage);
    }
}
