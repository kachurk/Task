package com.kkachur.task.executor;


import com.kkachur.task.dao.AccountDao;
import com.kkachur.task.dao.TransactionDao;
import com.kkachur.task.dto.TransactionDto;
import com.kkachur.task.exception.*;
import com.kkachur.task.model.Account;
import com.kkachur.task.model.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TransactionExecutorTest {

    @Test
    public void testExecuteTransaction() throws TransactionExecutionException, AccountNotFoundException {
        // given
        AccountDao accountDao = mock(AccountDao.class);
        TransactionDao transactionDao = mock(TransactionDao.class);

        TransactionExecutor executor = new TransactionExecutor();

        executor.setAccountDao(accountDao);
        executor.setTransactionDao(transactionDao);

        String requestId = UUID.randomUUID().toString();

        Account fromAccount = new Account(BigDecimal.TEN, "fromAccount");
        fromAccount.setId(1);

        Account toAccount = new Account(BigDecimal.TEN, "toAccount");
        toAccount.setId(2);

        BigDecimal transactionAmount = new BigDecimal(5);

        Transaction transaction = new Transaction(requestId, fromAccount, toAccount, transactionAmount);
        transaction.setId(1);

        TransactionDto request = new TransactionDto(requestId, fromAccount.getId(), toAccount.getId(), transactionAmount);

        // when
        when(transactionDao.findByRequestId(any())).thenReturn(Optional.empty());
        when(transactionDao.createTransaction(any())).thenReturn(transaction);

        when(accountDao.findByIdForUpdate(fromAccount.getId())).thenReturn(Optional.of(fromAccount));
        when(accountDao.findByIdForUpdate(toAccount.getId())).thenReturn(Optional.of(toAccount));

        Transaction executedTransaction = executor.executeTransaction(request);

        // then
        Assert.assertEquals(transaction, executedTransaction);
        Assert.assertEquals(fromAccount.getBalance(), transactionAmount);
        Assert.assertEquals(toAccount.getBalance(), transactionAmount.multiply(BigDecimal.valueOf(3)));

        verify(transactionDao, times(1)).findByRequestId(requestId);
        verify(accountDao, times(1)).findByIdForUpdate(fromAccount.getId());
        verify(accountDao, times(1)).findByIdForUpdate(toAccount.getId());
        verify(transactionDao, times(1)).createTransaction(any());
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testExecuteTransactionNotEnoughMoney() throws TransactionExecutionException, AccountNotFoundException {
        // given
        AccountDao accountDao = mock(AccountDao.class);
        TransactionDao transactionDao = mock(TransactionDao.class);

        TransactionExecutor executor = new TransactionExecutor();

        executor.setAccountDao(accountDao);
        executor.setTransactionDao(transactionDao);

        String requestId = UUID.randomUUID().toString();

        Account fromAccount = new Account(BigDecimal.TEN, "fromAccount");
        fromAccount.setId(1);

        Account toAccount = new Account(BigDecimal.TEN, "toAccount");
        toAccount.setId(2);

        BigDecimal transactionAmount = new BigDecimal(15);

        Transaction transaction = new Transaction(requestId, fromAccount, toAccount, transactionAmount);
        transaction.setId(1);

        TransactionDto request = new TransactionDto(requestId, fromAccount.getId(), toAccount.getId(), transactionAmount);

        // when
        when(transactionDao.findByRequestId(any())).thenReturn(Optional.empty());
        when(transactionDao.createTransaction(any())).thenReturn(transaction);

        when(accountDao.findByIdForUpdate(fromAccount.getId())).thenReturn(Optional.of(fromAccount));
        when(accountDao.findByIdForUpdate(toAccount.getId())).thenReturn(Optional.of(toAccount));

        executor.executeTransaction(request);
    }

    @Test(expected = SameAccountException.class)
    public void testExecuteTransactionSameAccountException() throws TransactionExecutionException, AccountNotFoundException {
        // given
        AccountDao accountDao = mock(AccountDao.class);
        TransactionDao transactionDao = mock(TransactionDao.class);

        TransactionExecutor executor = new TransactionExecutor();

        executor.setAccountDao(accountDao);
        executor.setTransactionDao(transactionDao);

        String requestId = UUID.randomUUID().toString();

        Account fromAccount = new Account(BigDecimal.TEN, "fromAccount");
        fromAccount.setId(1);

        BigDecimal transactionAmount = new BigDecimal(15);

        Transaction transaction = new Transaction(requestId, fromAccount, fromAccount, transactionAmount);
        transaction.setId(1);

        TransactionDto request = new TransactionDto(requestId, fromAccount.getId(), fromAccount.getId(), transactionAmount);

        // when
        when(transactionDao.findByRequestId(any())).thenReturn(Optional.empty());
        when(transactionDao.createTransaction(any())).thenReturn(transaction);

        when(accountDao.findByIdForUpdate(fromAccount.getId())).thenReturn(Optional.of(fromAccount));
        when(accountDao.findByIdForUpdate(fromAccount.getId())).thenReturn(Optional.of(fromAccount));

        executor.executeTransaction(request);
    }

    @Test(expected = TransactionAlreadyExecutedException.class)
    public void testExecuteTransactionSameRequestId() throws TransactionExecutionException, AccountNotFoundException {
        // given
        AccountDao accountDao = mock(AccountDao.class);
        TransactionDao transactionDao = mock(TransactionDao.class);

        TransactionExecutor executor = new TransactionExecutor();

        executor.setAccountDao(accountDao);
        executor.setTransactionDao(transactionDao);

        String requestId = UUID.randomUUID().toString();

        Account fromAccount = new Account(BigDecimal.TEN, "fromAccount");
        fromAccount.setId(1);

        Account toAccount = new Account(BigDecimal.TEN, "toAccount");
        toAccount.setId(2);

        BigDecimal transactionAmount = new BigDecimal(5);

        Transaction transaction = new Transaction(requestId, fromAccount, toAccount, transactionAmount);
        transaction.setId(1);

        TransactionDto request = new TransactionDto(requestId, fromAccount.getId(), toAccount.getId(), transactionAmount);

        // when
        when(transactionDao.findByRequestId(any())).thenReturn(Optional.empty());
        when(transactionDao.createTransaction(any())).thenReturn(transaction);

        when(accountDao.findByIdForUpdate(fromAccount.getId())).thenReturn(Optional.of(fromAccount));
        when(accountDao.findByIdForUpdate(toAccount.getId())).thenReturn(Optional.of(toAccount));

        executor.executeTransaction(request);

        when(transactionDao.findByRequestId(any())).thenReturn(Optional.of(transaction));

        executor.executeTransaction(request);
    }

}
