package com.kkachur.task.exception;


public class NotEnoughMoneyException extends TransactionExecutionException {

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
