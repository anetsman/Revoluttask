package com.alex.controller;

import com.alex.model.Transaction;
import com.alex.model.UserAccount;
import com.google.gson.JsonSyntaxException;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static spark.Spark.post;
import static spark.Spark.put;

public class TransactionController extends BaseController {

    public void init(){

        /**
         * @put
         * top up user account with
         * @param id with
         * @param amount specified
         */
        put("/user/:id/deposit/:amount", ((request, response) -> {
            String id = request.params(":id");
            String amount = request.params(":amount");
            UserAccount userAccount = userAccountService.findById(id);
            if (userAccount != null) {
                try {
                    userAccount.setBalance(userAccount.getBalance() + Integer.valueOf(amount));
                } catch (NumberFormatException e) {
                    response.status(HTTP_BAD_REQUEST);
                    return gson.toJson("specify valid amount");
                }
                return gson.toJson(userAccount);
            } else {
                response.status(HTTP_NOT_FOUND);
                return gson.toJson("user account not found");
            }
        }));


        /**
         * @put
         * withdraw account with
         * @param amount specified
         */
        put("/user/:id/withdraw/:amount", ((request, response) -> {
            String id = request.params(":id");
            String amount = request.params(":amount");
            UserAccount userAccount = userAccountService.findById(id);
            if (userAccount != null) {
                try {
                    userAccount.setBalance(userAccount.getBalance() - Integer.valueOf(amount));
                } catch (NumberFormatException e) {
                    response.status(HTTP_BAD_REQUEST);
                    return gson.toJson("specify valid amount");
                }
                return gson.toJson(userAccount);
            } else {
                response.status(HTTP_NOT_FOUND);
                return gson.toJson("user account not found");
            }
        }));

        /**
         * @post
         * send money from one
         * @param account to
         * @param another
         */
        post("/transaction", "application/json", ((request, response) -> {
            Transaction transaction;
            int amount;
            UserAccount fromAccount;
            UserAccount toAccount;

            try {
                transaction = gson.fromJson(request.body(), Transaction.class);
                amount = transaction.getAmount();
                fromAccount = userAccountService.findById(String.valueOf(transaction.getFromAccountId()));
                toAccount = userAccountService.findById(String.valueOf(transaction.getToAccountId()));
            } catch (JsonSyntaxException e) {
                response.status(HTTP_BAD_REQUEST);
                return gson.toJson("specify valid amount");
            }

            if (fromAccount != null || toAccount != null) {
                return gson.toJson(transactionService.makeTransaction(fromAccount, toAccount, amount));
            } else {
                response.status(HTTP_NOT_FOUND);
                return gson.toJson("user account not found");
            }
        }));
    }
}
