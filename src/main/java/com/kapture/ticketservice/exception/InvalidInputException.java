package com.kapture.ticketservice.exception;

public class InvalidInputException extends Exception{
    String errorMessage;
    public InvalidInputException(String errorMessage){
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
