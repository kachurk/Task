package com.kkachur.task.exception;


public class DuplicateTransactionException extends TransactionExecutionException {
    public DuplicateTransactionException(String message) {
        super(message);
    }
}
