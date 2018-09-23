package com.kkachur.task.exception;


public class TransactionAlreadyExecutedException extends TransactionExecutionException {
    public TransactionAlreadyExecutedException(String message) {
        super(message);
    }
}
