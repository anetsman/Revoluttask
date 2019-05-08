package com.alex;

import com.alex.controller.TransactionController;
import com.alex.controller.UserAccountController;
import com.alex.ioc.ConfigModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import static spark.Spark.port;

public class Application {

    public static void main(String[] args) {
        port(8080);
        registerModules();
    }

    private static void registerModules() {
        Injector injector = Guice.createInjector(new ConfigModule());
        injector.getInstance(UserAccountController.class).init();
        injector.getInstance(TransactionController.class).init();
    }
}
