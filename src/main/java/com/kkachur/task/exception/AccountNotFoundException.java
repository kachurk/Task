package com.kkachur.task.exception;


public class AccountNotFoundException extends Exception {

    private Long accountId;

    public AccountNotFoundException(Long accountId, String message) {
        super(message);
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
