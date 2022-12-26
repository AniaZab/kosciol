package com.apka.kosciol.exceptions;

public class DoesNotExistException extends Exception {
    public DoesNotExistException(String errorMessage) { //, Throwable err
        super(errorMessage);
    }
}
