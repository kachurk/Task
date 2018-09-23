package com.kkachur.task.dao;


import com.kkachur.task.exception.AccountNotFoundException;
import com.kkachur.task.exception.DuplicateAccountException;
import com.kkachur.task.model.Account;

import java.util.Optional;

public interface AccountDao {

    Account create(Account account) throws DuplicateAccountException;

    Optional<Account> findById(long id);

    Optional<Account> findByIdForUpdate(long id);

    void updateBalance(Account account) throws AccountNotFoundException;
}
