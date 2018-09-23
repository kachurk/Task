package com.kkachur.task.dto;

import com.kkachur.task.model.Transaction;

import java.math.BigDecimal;

public class TransactionDto extends ResponseEntity {

    private String requestId;

    private Long id;

    private Long fromAccountId;

    private Long toAccountId;

    private BigDecimal amount;

    public TransactionDto() {
    }

    public TransactionDto(String requestId, Long fromAccountId, Long toAccountId, BigDecimal amount) {
        this.requestId = requestId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public TransactionDto(Transaction transaction) {
        this.requestId = transaction.getRequestId();
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.fromAccountId = transaction.getFromAccount().getId();
        this.toAccountId = transaction.getToAccount().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
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

        TransactionDto that = (TransactionDto) o;

        if (requestId != null ? !requestId.equals(that.requestId) : that.requestId != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fromAccountId != null ? !fromAccountId.equals(that.fromAccountId) : that.fromAccountId != null)
            return false;
        if (toAccountId != null ? !toAccountId.equals(that.toAccountId) : that.toAccountId != null) return false;
        return amount != null ? amount.equals(that.amount) : that.amount == null;
    }

    @Override
    public int hashCode() {
        int result = requestId != null ? requestId.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (fromAccountId != null ? fromAccountId.hashCode() : 0);
        result = 31 * result + (toAccountId != null ? toAccountId.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}
