package com.wesp.service.impl;

import com.wesp.model.Order;
import com.wesp.model.Seller;
import com.wesp.model.Transaction;
import com.wesp.repository.SellerRepository;
import com.wesp.repository.TransactionRepository;
import com.wesp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private SellerRepository sellerRepository;

    @Override
    public Transaction createTransaction(Order order) {
        Seller seller = sellerRepository.findById(order.getSellerId()).get();
        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        transaction.setSeller(seller);
        transaction.setCustomer(order.getUser());

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionBySellerId(Seller seller) {
        return transactionRepository.findBySellerId(seller.getId());
    }

    @Override
    public List<Transaction> getAllTransaction() {

        return transactionRepository.findAll();
    }
}
