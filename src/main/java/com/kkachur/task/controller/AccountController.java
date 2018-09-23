package com.kkachur.task.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Response;
import com.kkachur.task.dao.EbeanAccountDao;
import com.kkachur.task.dto.AccountDto;
import com.kkachur.task.dto.Error;
import com.kkachur.task.dto.Errors;
import com.kkachur.task.dto.ResponseEntity;
import com.kkachur.task.exception.DuplicateAccountException;
import com.kkachur.task.model.Account;
import com.kkachur.task.validator.AccountValidator;

import java.util.Optional;

@Path("account")
public class AccountController {

    @Inject
    private EbeanAccountDao ebeanAccountDao;

    @Inject
    private AccountValidator accountValidator;

    @GetRoute("/:accountId")
    @JSON
    public ResponseEntity getAccount(@PathParam Long accountId, Response response) throws Exception {
        Optional<Account> accountOptional = ebeanAccountDao.findById(accountId);

        if (!accountOptional.isPresent()) {
            response.notFound();
            return Error.NOT_FOUND;
        }

        return new AccountDto(accountOptional.get());
    }

    @PostRoute("/")
    @JSON
    public ResponseEntity createAccount(@BodyParam AccountDto account, Response response) {
        try {

            Errors errors = accountValidator.validate(account);

            if (errors.hasErrors()) {
                response.badRequest();
                return errors;
            }

            return new AccountDto(ebeanAccountDao.create(new Account(account)));
        } catch (DuplicateAccountException e) {
            response.badRequest();
            return Error.ACCOUNT_ALREADY_EXISTS;
        }
    }
}
