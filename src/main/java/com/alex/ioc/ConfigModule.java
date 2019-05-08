package com.alex.ioc;

import com.alex.controller.TransactionController;
import com.alex.controller.UserAccountController;
import com.alex.service.TransactionService;
import com.alex.service.UserAccountService;
import com.alex.utils.TestUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Gson.class).toInstance(new GsonBuilder().create());
        bind(UserAccountService.class).toInstance(new UserAccountService());
        bind(UserAccountController.class).in(Singleton.class);
        bind(TransactionService.class).toInstance(new TransactionService());
        bind(TransactionController.class).in(Singleton.class);
        bind(TestUtils.class).toInstance(new TestUtils());
    }
}
