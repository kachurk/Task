package com.kkachur.task.dto;


import com.kkachur.task.model.Account;

import java.math.BigDecimal;

public class AccountDto extends ResponseEntity {


    private Long id;
    private String userName;
    private BigDecimal balance;

    public AccountDto() {
    }

    public AccountDto(String userName, BigDecimal balance) {
        this.userName = userName;
        this.balance = balance;
    }

    public AccountDto(Account account) {
        this.id = account.getId();
        this.balance = account.getBalance();
        this.userName = account.getUserName();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountDto that = (AccountDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        return balance != null ? balance.equals(that.balance) : that.balance == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }
}
