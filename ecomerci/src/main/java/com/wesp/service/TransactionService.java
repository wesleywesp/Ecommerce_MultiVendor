package com.wesp.service;

import com.wesp.model.Order;
import com.wesp.model.Seller;
import com.wesp.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Order order);
    List<Transaction> getTransactionBySellerId(Seller seller);
    List<Transaction> getAllTransaction();

}
