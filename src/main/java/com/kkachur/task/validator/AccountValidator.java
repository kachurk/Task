package com.kkachur.task.validator;


import com.blade.ioc.annotation.Bean;
import com.kkachur.task.dto.AccountDto;
import com.kkachur.task.dto.Error;
import com.kkachur.task.dto.Errors;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

@Bean
public class AccountValidator {

    public Errors validate(AccountDto accountDto) {
        Errors errors = new Errors();

        if (accountDto.getBalance() == null
                || accountDto.getBalance().compareTo(BigDecimal.ZERO) < 1) {
            errors.addError(new Error(400, "balance should contain correct value"));
        }

        if (StringUtils.isEmpty(accountDto.getUserName())) {
            errors.addError(new Error(400, "userName should contain correct value"));
        }

        return errors;
    }

}
