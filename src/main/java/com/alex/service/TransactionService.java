package com.alex.service;

import com.alex.model.Transaction;
import com.alex.model.UserAccount;

public class TransactionService {
    public Transaction makeTransaction(UserAccount fromAccount, UserAccount toAccount, int amount) {
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        return new Transaction(amount, fromAccount.getId(), toAccount.getId());
    }
}
