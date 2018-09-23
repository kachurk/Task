package com.kkachur.task;


import com.blade.Blade;
import com.kkachur.task.dao.AccountDao;
import com.kkachur.task.exception.DuplicateAccountException;
import com.kkachur.task.handler.InternalServerExceptionHandler;
import com.kkachur.task.model.Account;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import org.avaje.datasource.DataSourceConfig;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws Exception {
        Blade blade = Blade.of()
                .exceptionHandler(new InternalServerExceptionHandler())
                .get("/", ctx -> ctx.render("application.html"));

        blade.start(Application.class, args);
    }

}
