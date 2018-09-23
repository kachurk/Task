package com.kkachur.task.exception;


public class IncorrectTransactionAmount extends TransactionExecutionException {

    public IncorrectTransactionAmount(String message) {
        super(message);
    }
}
