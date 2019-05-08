package com.alex.service;

import com.alex.model.UserAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UserAccountService {
    private static Map<String, UserAccount> users = new HashMap<>();
    private static final AtomicInteger count = new AtomicInteger(0);

    public UserAccount findById(String id) {
        return users.get(id);
    }

    public UserAccount add() {
        int currentId = count.incrementAndGet();
        UserAccount userAccount = new UserAccount(currentId);
        users.put(String.valueOf(currentId), userAccount);
        return userAccount;
    }

    public List<UserAccount> listUsers() {
        return new ArrayList<>(users.values());
    }

    public void delete(String id) {
        users.remove(id);
    }

    public UserAccountService() {
    }
}
