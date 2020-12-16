package com.db.awmd.challenge.exception;

public class NoAccountsPresentException extends Exception{
    public NoAccountsPresentException() {
        super("No Accounts Available");
    }
}
