package com.kkachur.task.controller;


import com.blade.kit.JsonKit;
import com.blade.test.BladeApplication;
import com.blade.test.BladeTestRunner;
import com.kkachur.task.Application;
import com.kkachur.task.dto.AccountDto;
import com.kkachur.task.dto.TransactionDto;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.UUID;

@RunWith(BladeTestRunner.class)
@BladeApplication(Application.class)
public class TransactionControllerTest {

    @Test
    public void testExecuteTransaction() throws UnirestException {
        // given
        AccountDto fromAccount = createAccount(new AccountDto("fromAccount", BigDecimal.valueOf(100)));
        AccountDto toAccount = createAccount(new AccountDto("toAccount", BigDecimal.valueOf(100)));

        String requestId = UUID.randomUUID().toString();
        Long fromAccountId = fromAccount.getId();
        Long toAccountId = toAccount.getId();
        BigDecimal transactionAmount = new BigDecimal(50);

        TransactionDto transactionDto = new TransactionDto(requestId,
                fromAccountId,
                toAccountId,
                transactionAmount);

        // when
        HttpRequest httpRequest = Unirest
                .post("http://127.0.0.1:9000/transaction")
                .body(JsonKit.toString(transactionDto))
                .getHttpRequest();

        // then
        Assert.assertEquals("transaction execution is failed", HttpStatus.SC_OK, httpRequest.asString().getStatus());

        // when
        AccountDto fromAccountAfterTransaction = getAccount(fromAccountId);
        AccountDto toAccountAfterTransaction = getAccount(toAccountId);

        // then
        Assert.assertEquals("balance for from account is not changed",
                fromAccount.getBalance().subtract(transactionAmount), fromAccountAfterTransaction.getBalance());

        Assert.assertEquals("balance for to account is not changed",
                toAccount.getBalance().add(transactionAmount), toAccountAfterTransaction.getBalance());
    }

    @Test
    public void testExecuteTransactionNotEnoughMoney() throws UnirestException {
        // given
        AccountDto fromAccount = createAccount(new AccountDto("fromAccount", BigDecimal.valueOf(100)));
        AccountDto toAccount = createAccount(new AccountDto("toAccount", BigDecimal.valueOf(100)));

        String requestId = UUID.randomUUID().toString();
        Long fromAccountId = fromAccount.getId();
        Long toAccountId = toAccount.getId();
        BigDecimal transactionAmount = fromAccount.getBalance().add(BigDecimal.TEN);

        TransactionDto transactionDto = new TransactionDto(requestId,
                fromAccountId,
                toAccountId,
                transactionAmount);

        // when
        HttpRequest httpRequest = Unirest
                .post("http://127.0.0.1:9000/transaction")
                .body(JsonKit.toString(transactionDto))
                .getHttpRequest();

        // then
        Assert.assertEquals("transaction should not be executed but it's", HttpStatus.SC_BAD_REQUEST, httpRequest.asString().getStatus());
    }

    @Test
    public void testExecuteTransactionSameRequestId() throws UnirestException {
        // given
        AccountDto fromAccount = createAccount(new AccountDto("fromAccount", BigDecimal.valueOf(100)));
        AccountDto toAccount = createAccount(new AccountDto("toAccount", BigDecimal.valueOf(100)));


        String requestId = UUID.randomUUID().toString();
        Long fromAccountId = fromAccount.getId();
        Long toAccountId = toAccount.getId();
        BigDecimal transactionAmount = new BigDecimal(50);

        TransactionDto transactionDto = new TransactionDto(requestId,
                fromAccountId,
                toAccountId,
                transactionAmount);

        // when
        HttpRequest firstHttpRequest = Unirest
                .post("http://127.0.0.1:9000/transaction")
                .body(JsonKit.toString(transactionDto))
                .getHttpRequest();

        // then
        Assert.assertEquals("transaction should be executed ", HttpStatus.SC_OK, firstHttpRequest.asString().getStatus());

        // when
        HttpRequest secondHttpRequest = Unirest
                .post("http://127.0.0.1:9000/transaction")
                .body(JsonKit.toString(transactionDto))
                .getHttpRequest();

        // then
        Assert.assertEquals("transaction should not be executed but it's", HttpStatus.SC_BAD_REQUEST, secondHttpRequest.asString().getStatus());
    }

    @Test
    public void testExecuteTransactionSameAccounts() throws UnirestException {
        // given
        AccountDto fromAccount = createAccount(new AccountDto("fromAccount", BigDecimal.valueOf(100)));

        String requestId = UUID.randomUUID().toString();
        Long fromAccountId = fromAccount.getId();
        Long toAccountId = fromAccount.getId();
        BigDecimal transactionAmount = new BigDecimal(50);

        TransactionDto transactionDto = new TransactionDto(requestId,
                fromAccountId,
                toAccountId,
                transactionAmount);

        // when
        HttpRequest httpRequest = Unirest
                .post("http://127.0.0.1:9000/transaction")
                .body(JsonKit.toString(transactionDto))
                .getHttpRequest();

        // then
        Assert.assertEquals("transaction should not be executed but it's", HttpStatus.SC_BAD_REQUEST, httpRequest.asString().getStatus());
    }


    public AccountDto createAccount(AccountDto accountDto) throws UnirestException {
        HttpRequest httpRequest = Unirest
                .post("http://127.0.0.1:9000/account")
                .body(JsonKit.toString(accountDto))
                .getHttpRequest();

        return JsonKit
                .formJson(httpRequest.asString().getBody(),
                        AccountDto.class);
    }

    private AccountDto getAccount(Long fromAccountId) throws UnirestException {
        GetRequest getRequest = Unirest.get("http://127.0.0.1:9000/account/" + fromAccountId);

        return JsonKit.formJson(
                getRequest.asString().getBody(),
                AccountDto.class);
    }


}
