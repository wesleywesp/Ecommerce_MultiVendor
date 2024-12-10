package com.wesp.controller;

import com.wesp.infra.exception.SellerException;
import com.wesp.model.Seller;
import com.wesp.model.Transaction;
import com.wesp.service.SellerService;
import com.wesp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final SellerService sellerService;

    @GetMapping("/seller")
    public ResponseEntity<List<Transaction>> getTransactionBySeller(@RequestHeader("Authorization") String token) throws SellerException {
        Seller seller = sellerService.getSellerProfile(token);
        return ResponseEntity.ok(transactionService.getTransactionBySellerId(seller));
    }
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransaction() {
        return ResponseEntity.ok(transactionService.getAllTransaction());
    }
}
