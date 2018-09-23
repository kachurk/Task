package com.kkachur.task.controller;

import com.blade.kit.JsonKit;
import com.blade.test.BladeApplication;
import com.blade.test.BladeTestRunner;
import com.kkachur.task.Application;
import com.kkachur.task.dto.AccountDto;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

@RunWith(BladeTestRunner.class)
@BladeApplication(Application.class)
public class AccountControllerTest {

    @Test
    public void testCreateAndGetAccount() throws UnirestException {
        // given
        AccountDto account = new AccountDto("testAccount", BigDecimal.TEN);

        // when
        HttpRequest httpRequest = Unirest
                .post("http://127.0.0.1:9000/account")
                .body(JsonKit.toString(account))
                .getHttpRequest();

        AccountDto responsePostBody = JsonKit
                .formJson(httpRequest.asString().getBody(),
                        AccountDto.class);

        // then
        Assert.assertEquals("account creation is failed", HttpStatus.SC_OK, httpRequest.asString().getStatus());

        // when
        GetRequest getRequest = Unirest.get("http://127.0.0.1:9000/account/" + responsePostBody.getId());

        AccountDto persistedAccount = JsonKit.formJson(
                getRequest.asString().getBody(),
                AccountDto.class);

        // then
        Assert.assertEquals("get account request is failed ", HttpStatus.SC_OK, getRequest.asString().getStatus());
        Assert.assertEquals("response of creation and get response are different", responsePostBody, persistedAccount);
    }

}
