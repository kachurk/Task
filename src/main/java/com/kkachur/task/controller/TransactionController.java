package com.kkachur.task.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Response;
import com.kkachur.task.dao.EbeanTransactionDao;
import com.kkachur.task.dto.Error;
import com.kkachur.task.dto.Errors;
import com.kkachur.task.dto.ResponseEntity;
import com.kkachur.task.dto.TransactionDto;
import com.kkachur.task.exception.*;
import com.kkachur.task.model.Transaction;
import com.kkachur.task.executor.TransactionExecutor;
import com.kkachur.task.validator.TransactionValidator;

import java.util.Optional;

@Path("transaction")
public class TransactionController {

    public static final String ACCOUNT_NOT_FOUND_TEMPLATE = "Account is not found, id = %s";

    @Inject
    private EbeanTransactionDao ebeanTransactionDao;

    @Inject
    private TransactionExecutor transactionExecutor;

    @Inject
    private TransactionValidator transactionValidator;

    @GetRoute("/:transactionId")
    @JSON
    public ResponseEntity getTransaction(@PathParam Long transactionId, Response response) {
        Optional<Transaction> transactionOptional = ebeanTransactionDao.findById(transactionId);

        if (!transactionOptional.isPresent()) {
            response.notFound();
            return Error.NOT_FOUND;
        }

        return new TransactionDto(transactionOptional.get());
    }

    @PostRoute("/")
    @JSON
    public ResponseEntity executeTransaction(@BodyParam TransactionDto transactionDto, Response response) {
        try {
            Errors errors = transactionValidator.validate(transactionDto);

            if (errors.hasErrors()) {
                response.badRequest();
                return errors;
            }

            return new TransactionDto(transactionExecutor.executeTransaction(transactionDto));
        } catch (AccountNotFoundException e) {
            return makeAccountNotFoundError(e.getAccountId(), response);
        } catch (TransactionExecutionException e) {
            return createError(e, response);
        }
    }

    private ResponseEntity createError(TransactionExecutionException e, Response response) {
        response.badRequest();
        return new Error(400, e.getMessage());
    }

    private ResponseEntity makeAccountNotFoundError(Long accountId, Response response) {
        response.badRequest();
        return new Error(400, String.format(ACCOUNT_NOT_FOUND_TEMPLATE, accountId));
    }
}
