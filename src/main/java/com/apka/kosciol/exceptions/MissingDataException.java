package com.apka.kosciol.exceptions;

public class MissingDataException extends Exception {
    public MissingDataException(String errorMessage) { //, Throwable err
        super(errorMessage);
    }
}
