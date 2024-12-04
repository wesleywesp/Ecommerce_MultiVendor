package com.wesp.controller;

import com.wesp.infra.exception.ProductException;
import com.wesp.infra.exception.SellerException;
import com.wesp.model.Product;
import com.wesp.model.Seller;
import com.wesp.request.CreateProductRequestDTO;
import com.wesp.request.ProductRequestDTO;
import com.wesp.service.ProductService;
import com.wesp.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/sellers")
public class SellerProductController {
    private final ProductService productService;
    private final SellerService sellerService;


    @GetMapping
    public ResponseEntity<List<Product>> getAllProductsBySellerId(@RequestHeader("Authorization") String token) throws SellerException {
        Seller seller = sellerService.getSellerProfile(token);
        List<Product> products = productService.findProductsBySellerId(seller.getId());
        return ResponseEntity.ok(products);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Product> createProduct(@RequestHeader("Authorization") String token, @RequestBody CreateProductRequestDTO product) throws SellerException {
        Seller seller = sellerService.getSellerProfile(token);
        Product newProduct = productService.createProduct(product, seller);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    @Transactional
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) throws SellerException, ProductException {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.noContent().build();
        } catch (ProductException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{productId}")
    @Transactional
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDTO product) throws ProductException {
          productService.updateProduct(productId, product);
            return ResponseEntity.ok().build();
    }
}
