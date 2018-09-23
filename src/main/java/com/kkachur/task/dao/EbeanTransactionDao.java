package com.kkachur.task.dao;


import com.blade.ioc.annotation.Bean;
import com.kkachur.task.exception.DuplicateAccountException;
import com.kkachur.task.exception.DuplicateTransactionException;
import com.kkachur.task.model.Account;
import com.kkachur.task.model.Transaction;
import io.ebean.Ebean;

import java.util.Optional;

@Bean
public class EbeanTransactionDao implements TransactionDao {

    @Override
    public Optional<Transaction> findById(Long transactionId) {
        return Ebean.createQuery(Transaction.class)
                .where().idEq(transactionId).findOneOrEmpty();
    }

    @Override
    public Transaction createTransaction(Transaction transaction) throws DuplicateTransactionException {

        Optional<Transaction> persistedTransactionOptional = findById(transaction.getId());

        if (persistedTransactionOptional.isPresent()) {
            throw new DuplicateTransactionException("transaction already exists, id = " + transaction.getId());
        }

        transaction.save();

        return transaction;
    }

    @Override
    public Optional<Transaction> findByRequestId(String requestId) {
        return Ebean.createQuery(Transaction.class)
                .where().eq("requestId", requestId).findOneOrEmpty();
    }

}
