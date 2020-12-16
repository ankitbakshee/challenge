package com.db.awmd.challenge.exception;

public class InsufficientAmountException extends RuntimeException{
    public InsufficientAmountException() {
        super("Insufficient Balance to transfer");
    }
}
