package com.db.awmd.challenge.exception;

public class SameAccountTransferException extends RuntimeException {

    public SameAccountTransferException() {
        super("Can not Transfer to same account");
    }
}
