package com.kkachur.task.exception;


public class SameAccountException extends TransactionExecutionException {
    public SameAccountException(String message) {
        super(message);
    }
}
