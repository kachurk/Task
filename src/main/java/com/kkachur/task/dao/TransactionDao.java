package com.kkachur.task.dao;


import com.kkachur.task.exception.DuplicateTransactionException;
import com.kkachur.task.model.Transaction;

import java.util.Optional;

public interface TransactionDao {

    Optional<Transaction> findById(Long transactionId);

    Transaction createTransaction(Transaction transaction) throws DuplicateTransactionException;

    Optional<Transaction> findByRequestId(String requestId);
}
