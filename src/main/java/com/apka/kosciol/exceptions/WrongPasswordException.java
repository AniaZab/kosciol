package com.apka.kosciol.exceptions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException(String errorMessage) { //, Throwable err
        super(errorMessage);
    }
}
