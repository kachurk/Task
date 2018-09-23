package com.kkachur.task.model;


import com.kkachur.task.dto.AccountDto;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="account")
public class Account extends Model {

    @Id
    private long id;
    private BigDecimal balance;
    private String userName;

    public Account() {
    }

    public Account(AccountDto accountDto) {
        this.userName = accountDto.getUserName();
        this.balance = accountDto.getBalance();
    }

    public Account(BigDecimal balance, String userName) {
        this.balance = balance;
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (id != account.id) return false;
        if (balance != null ? !balance.equals(account.balance) : account.balance != null) return false;
        return userName != null ? userName.equals(account.userName) : account.userName == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", userName='" + userName + '\'' +
                '}';
    }

    public void update(Account account){
        this.id = account.id;
        this.balance = account.balance;
        this.userName = account.userName;
    }
}
