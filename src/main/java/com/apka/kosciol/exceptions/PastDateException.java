package com.apka.kosciol.exceptions;

public class PastDateException extends Exception {
    public PastDateException(String errorMessage) { //, Throwable err
        super(errorMessage);
    }
}
