package com.kkachur.task.executor;


import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.blade.kit.Assert;
import com.kkachur.task.dao.AccountDao;
import com.kkachur.task.dao.TransactionDao;
import com.kkachur.task.dto.TransactionDto;
import com.kkachur.task.exception.*;
import com.kkachur.task.model.Account;
import com.kkachur.task.model.Transaction;
import io.ebean.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Bean
public class TransactionExecutor {

    @Inject
    private TransactionDao transactionDao;

    @Inject
    private AccountDao accountDao;

    /**
     * Checks that request is not already executed, it needs to support idempotency of request.
     * (if client could not receive response due network issues, can retry and receive response
     * that transaction is already executed).
     * <p>
     * Finds accounts for updateBalance (account will be locked for this transaction)
     * in the same order (sorts from smallest to largest), it needs to avoid
     * deadlock.
     * <p>
     * Checks that there is enough money for transfer, transfers money
     * and creates transaction row in database.
     * <p>
     * Note: will be executed in a transaction
     *
     * @throws {@link TransactionAlreadyExecutedException} if request is already executed
     * @throws {@link SameAccountException} if the from and to accounts equal
     * @throws {@link AccountNotFoundException} if could not find the from or the to account
     * @throws {@link NotEnoughMoneyException} if the amount of transaction is more than balance of the from account
     * @throws {@link NullPointerException} if the required fields are null
     */
    @Transactional
    public Transaction executeTransaction(TransactionDto transactionDto) throws TransactionAlreadyExecutedException,
            SameAccountException, AccountNotFoundException, NotEnoughMoneyException, DuplicateTransactionException, IncorrectTransactionAmount {

        Optional<Transaction> transactionOptional = transactionDao.findByRequestId(transactionDto.getRequestId());

        if (transactionOptional.isPresent()) {
            throw new TransactionAlreadyExecutedException(
                    "Transaction with request id = " + transactionDto.getRequestId() + " is already executed");
        }

        return transferMoney(transactionDto, findAccounts(transactionDto));
    }

    private Transaction transferMoney(TransactionDto transactionDto,
                                      Map<Long, Optional<Account>> accounts) throws AccountNotFoundException, NotEnoughMoneyException, DuplicateTransactionException, IncorrectTransactionAmount {
        Long fromAccountId = transactionDto.getFromAccountId();
        Long toAccountId = transactionDto.getToAccountId();

        Account fromAccount = accounts.get(fromAccountId)
                .orElseThrow(() -> new AccountNotFoundException(fromAccountId, "Could not find from account. " +
                        "From account should be present"));

        Account toAccount = accounts.get(toAccountId)
                .orElseThrow(() -> new AccountNotFoundException(toAccountId, "Could not find to account. " +
                        "To account should be present"));

        BigDecimal transactionAmount = transactionDto.getAmount();
        BigDecimal actualBalance = fromAccount.getBalance();

        if (actualBalance.compareTo(transactionAmount) < 0) {
            throw new NotEnoughMoneyException("There is no enough money for the transaction");
        }

        if (transactionAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IncorrectTransactionAmount("Transaction amount should be positive");
        }

        fromAccount.setBalance(actualBalance.subtract(transactionAmount));
        toAccount.setBalance(toAccount.getBalance().add(transactionAmount));

        accountDao.updateBalance(fromAccount);
        accountDao.updateBalance(toAccount);

        return transactionDao.createTransaction(
                new Transaction(transactionDto.getRequestId(), fromAccount, toAccount, transactionAmount));
    }

    private Map<Long, Optional<Account>> findAccounts(TransactionDto transactionDto) throws SameAccountException {
        Long fromAccountId = transactionDto.getFromAccountId();
        Long toAccountId = transactionDto.getToAccountId();

        if (fromAccountId.compareTo(toAccountId) == 0) {
            throw new SameAccountException("Accounts should not be same in a transaction");
        }

        Map<Long, Optional<Account>> accounts = new HashMap<>(2);

        Long minId = Math.min(fromAccountId, toAccountId);
        Long maxId = Math.max(fromAccountId, toAccountId);

        accounts.put(minId, accountDao.findByIdForUpdate(minId));
        accounts.put(maxId, accountDao.findByIdForUpdate(maxId));

        return accounts;
    }

    public void setTransactionDao(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
