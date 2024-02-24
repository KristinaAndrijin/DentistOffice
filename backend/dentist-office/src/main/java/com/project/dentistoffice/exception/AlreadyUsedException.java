package com.project.dentistoffice.exception;

public class AlreadyUsedException extends RuntimeException {
    public AlreadyUsedException(String message)
    {
        super(message);
    }
}
