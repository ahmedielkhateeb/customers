package com.demo.customers.exceptions.custom;

public class NotAcceptableException extends Exception{
    private static final long serialVersionUID = 1L;

    private String errorMessage;

    public NotAcceptableException() {
        super();
    }

    public NotAcceptableException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

