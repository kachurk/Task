package com.kkachur.task.loader;

import com.blade.Blade;
import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.blade.loader.BladeLoader;
import com.kkachur.task.dao.AccountDao;
import com.kkachur.task.model.Account;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import org.avaje.datasource.DataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;

@Bean
public class InitialDataLoader implements BladeLoader {

    private final static Logger LOGGER = LoggerFactory.getLogger(InitialDataLoader.class);

    @Inject
    private AccountDao accountDao;

    @Override
    public void load(Blade blade) {
        LOGGER.info("initial load started {}", new Date());
        try {
            accountDao.create(new Account(new BigDecimal(100), "firstAccount"));
            accountDao.create(new Account(new BigDecimal(100), "secondAccount"));
        } catch (Exception e) {
            LOGGER.error("error when inserting initial data", e);
        }

        LOGGER.info("initial load finished {}", new Date());
    }
}
