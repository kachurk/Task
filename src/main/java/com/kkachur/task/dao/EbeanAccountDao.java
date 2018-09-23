package com.kkachur.task.dao;


import com.blade.ioc.annotation.Bean;
import com.kkachur.task.exception.AccountNotFoundException;
import com.kkachur.task.exception.DuplicateAccountException;
import com.kkachur.task.model.Account;
import io.ebean.Ebean;

import java.util.Optional;

@Bean
public class EbeanAccountDao implements AccountDao {

    @Override
    public Account create(Account account) throws DuplicateAccountException {

        Optional<Account> persistedAccountOptional = findById(account.getId());

        if (persistedAccountOptional.isPresent()) {
            throw new DuplicateAccountException("account already exists, id = " + account.getId());
        }

        account.save();

        return account;
    }

    @Override
    public Optional<Account> findById(long id) {
        return Optional.ofNullable(Ebean.find(Account.class, id));
    }

    @Override
    public Optional<Account> findByIdForUpdate(long id) {
        return Ebean.createQuery(Account.class).forUpdate()
                .where().idEq(id).findOneOrEmpty();
    }

    @Override
    public void updateBalance(Account account) throws AccountNotFoundException {
        Account persistedAccount = findById(account.getId()).orElseThrow(() -> new AccountNotFoundException(account.getId(),
                "Could not update balance because account is not found id db"));

        persistedAccount.setBalance(account.getBalance());
        persistedAccount.update();
    }
}
