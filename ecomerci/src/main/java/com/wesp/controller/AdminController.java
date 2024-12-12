package com.wesp.controller;

import com.wesp.domain.AccountStatus;
import com.wesp.infra.exception.SellerException;
import com.wesp.model.Seller;
import com.wesp.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AdminController {
    private final SellerService sellerService;

    @PatchMapping("/seller/{id}/status/{status}")
    public ResponseEntity<Seller> updateSellerStatus(@PathVariable Long id, @PathVariable AccountStatus status) throws SellerException {
        Seller updadeSeller = sellerService.updateSellerAccountStatus(id, status);
        return ResponseEntity.ok(updadeSeller);
    }
}
