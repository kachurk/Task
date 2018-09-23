package com.kkachur.task.validator;

import com.blade.ioc.annotation.Bean;
import com.kkachur.task.dto.Error;
import com.kkachur.task.dto.Errors;
import com.kkachur.task.dto.TransactionDto;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

@Bean
public class TransactionValidator {

    public Errors validate(TransactionDto transactionDto) {

        Errors errors = new Errors();

        if (StringUtils.isEmpty(transactionDto.getRequestId())) {
            errors.addError(new Error(400, "requestId is required field, need to send correct value"));
        }

        if (transactionDto.getAmount() == null) {
            errors.addError(new Error(400, "amount is required field, need to send correct value"));
        } else if (transactionDto.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            errors.addError(new Error(400, "amount should be positive number"));
        }

        if (transactionDto.getFromAccountId() == null) {
            errors.addError(new Error(400, "fromAccountId is required field, need to send correct value"));
        }

        if (transactionDto.getToAccountId() == null) {
            errors.addError(new Error(400, "toAccountId is required field, need to send correct value"));
        }

        return errors;
    }

}
