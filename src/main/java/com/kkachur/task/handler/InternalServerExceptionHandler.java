package com.kkachur.task.handler;


import com.blade.kit.JsonKit;
import com.blade.mvc.handler.DefaultExceptionHandler;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.kkachur.task.dto.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InternalServerExceptionHandler extends DefaultExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(InternalServerExceptionHandler.class);


    /**
     * Handles unexpected exception, logs and writes error json to response.
     */
    @Override
    protected void handleException(Exception e, Request request, Response response) {
        LOGGER.error("Unknown error when execute: ", e);

        response.status(500);
        response.text(JsonKit.toString(Error.INTERNAL_SERVER_ERROR));
    }
}
