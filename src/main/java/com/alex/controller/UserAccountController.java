package com.alex.controller;

import com.alex.model.UserAccount;

import static java.net.HttpURLConnection.*;
import static spark.Spark.*;

public class UserAccountController extends BaseController {

    public UserAccountController() {
    }

    public void init() {
        /**
         * @post
         * creates user account
         * @return created user account
         */
        post("/user/add", (request, response) -> {
            UserAccount userAccount = userAccountService.add();
            response.status(HTTP_CREATED);
            return gson.toJson(userAccount);
        });

        /**
         * @get
         * @return user with
         * @param id
         */
        get("/user/:id", (request, response) -> {
            UserAccount userAccount = userAccountService.findById(request.params(":id"));
            if (userAccount != null) {
                return gson.toJson(userAccount);
            } else {
                response.status(HTTP_NOT_FOUND);
                return gson.toJson("userAccount not found");
            }
        });

        /**
         * @get
         * @return all users
         */
        get("/users", (request, response) -> gson.toJson(userAccountService.listUsers()));

        /**
         * @delete
         * remove user with
         * @param id
         */
        delete("/user/:id", (request, response) -> {
            String id = request.params(":id");
            UserAccount userAccount = userAccountService.findById(id);
            if (userAccount != null) {
                userAccountService.delete(id);
                return gson.toJson("userAccount with id " + id + " is deleted!");
            } else {
                response.status(HTTP_NOT_FOUND);
                return gson.toJson("userAccount not found");
            }
        });
    }
}
