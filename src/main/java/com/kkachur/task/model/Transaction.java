package com.kkachur.task.model;


import io.ebean.Model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="transaction")
public class Transaction extends Model {

    @Id
    private long id;

    @Column(unique = true)
    private String requestId;

    @ManyToOne
    private Account fromAccount;

    @ManyToOne
    private Account toAccount;

    private BigDecimal amount;


    public Transaction(String requestId, Account fromAccount, Account toAccount, BigDecimal amount) {
        this.requestId = requestId;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (id != that.id) return false;
        if (requestId != null ? !requestId.equals(that.requestId) : that.requestId != null) return false;
        if (fromAccount != null ? !fromAccount.equals(that.fromAccount) : that.fromAccount != null) return false;
        if (toAccount != null ? !toAccount.equals(that.toAccount) : that.toAccount != null) return false;
        return amount != null ? amount.equals(that.amount) : that.amount == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (requestId != null ? requestId.hashCode() : 0);
        result = 31 * result + (fromAccount != null ? fromAccount.hashCode() : 0);
        result = 31 * result + (toAccount != null ? toAccount.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}
