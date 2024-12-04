package com.wesp.service;

import com.wesp.infra.exception.ProductException;
import com.wesp.model.Product;
import com.wesp.model.Seller;
import com.wesp.request.CreateProductRequestDTO;
import com.wesp.request.ProductRequestDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;


public interface ProductService {
    public Product createProduct(CreateProductRequestDTO req, Seller seller);
    public void deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId, ProductRequestDTO req) throws ProductException;

    public Product findProductById(Long productId) throws ProductException;
    List<Product> searchProducts(String query);
    public Page<Product> getAllProducts(
            String Category,
            String brand,
            String colors,
            String Sizes,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            BigDecimal minDiscount,
            String sort,
            String stock,
            Integer pageNumber
    );

    public List<Product> findProductsBySellerId(Long sellerId);
//    public List<Product> findProductsBySeller(Seller seller);
}
