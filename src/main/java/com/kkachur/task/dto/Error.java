package com.kkachur.task.dto;


import com.blade.kit.json.JsonIgnore;

public class Error extends ResponseEntity {

    @JsonIgnore
    public static final Error NOT_FOUND = new Error(404, "Resource is not found");
    @JsonIgnore
    public static final Error ACCOUNT_ALREADY_EXISTS = new Error(400, "Account already exists");
    @JsonIgnore
    public static final Error INTERNAL_SERVER_ERROR = new Error(500, "Internal server error");
    @JsonIgnore
    public static final Error TRANSACTION_ALREADY_EXECUTED = new Error(200, "Transaction with this id is already completed successfully");
    @JsonIgnore
    public static final Error SAME_ACCOUNTS_IN_TRANSACTION = new Error(400, "Accounts should be different in transaction");
    @JsonIgnore
    public static final Error NOT_ENOUGH_MONEY_FOR_TRANSACTION = new Error(400, "There is not enough money for transaction");
    @JsonIgnore
    public static Error INCORRECT_TRANSACTION_AMOUNT = new Error(400, "Transaction amount should be positive");

    private int errorCode;
    private String errorMessage;

    public Error(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
