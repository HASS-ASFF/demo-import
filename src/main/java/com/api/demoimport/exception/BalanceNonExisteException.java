package com.api.demoimport.exception;

public class BalanceNonExisteException extends RuntimeException{
    public BalanceNonExisteException(String message) {
        super(message);
    }
}
