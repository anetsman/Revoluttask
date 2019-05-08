package com.alex.controller;

import com.alex.ioc.ConfigModule;
import com.alex.service.TransactionService;
import com.alex.service.UserAccountService;
import com.google.gson.Gson;
import com.google.inject.Inject;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GuiceExtension.class)
@IncludeModule(ConfigModule.class)
abstract class BaseController {
    @Inject
    Gson gson;

    @Inject
    UserAccountService userAccountService;

    @Inject
    TransactionService transactionService;

}
