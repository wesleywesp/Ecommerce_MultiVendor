package com.wesp.service.impl;

import com.wesp.infra.exception.ProductException;
import com.wesp.model.Category;
import com.wesp.model.Product;
import com.wesp.model.Seller;
import com.wesp.repository.CategoryRepository;
import com.wesp.repository.ProductRepository;
import com.wesp.request.CreateProductRequestDTO;
import com.wesp.request.ProductRequestDTO;
import com.wesp.service.ProductService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProductRequestDTO req, Seller seller) {
        Category category = categoryRepository.findByCategoryId(req.getCategory());
        if(category == null){
            category = new Category();
            category.setCategoryId(req.getCategory());
            category.setLevel(1);
            categoryRepository.save(category);
        }
        Category category2 = categoryRepository.findByCategoryId(req.getCategory());
            if(category2 == null) {
                category2 = new Category();
                category2.setCategoryId(req.getCategory2());
                category2.setLevel(2);
                category2.setParentCategory(category);
                categoryRepository.save(category2);
            }
            Category category3 = categoryRepository.findByCategoryId(req.getCategory3());
                if(category3 == null){
                    category3 = new Category();
                    category3.setCategoryId(req.getCategory3());
                    category3.setLevel(3);
                    category3.setParentCategory(category2);
                    categoryRepository.save(category3);
                }
        BigDecimal desconto = calculateDiscountPercentage(req.getSellingPrice(), req.getMrpPrice());



        Product product = new Product();
        product.setSeller(seller);
        product.setCategory(category3);
        product.setDescription(req.getDescription());
        product.setCreatedDate(LocalDateTime.now());
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setImages(req.getImages());
        product.setSizes(Collections.singletonList(req.getSizes()));
        product.setSellingPrice(req.getSellingPrice());
        product.setMrpPrice(req.getMrpPrice());
        product.setDiscountPercentage(desconto);

        return productRepository.save(product);
    }


    private BigDecimal calculateDiscountPercentage(BigDecimal sellingPrice, BigDecimal mrpPrice) {
        if (mrpPrice.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("MRP price cannot be zero");
        }
        // Definir precisão e modo de arredondamento
        return mrpPrice.subtract(sellingPrice)
                .divide(mrpPrice, 2, RoundingMode.HALF_UP) // Precisão de 2 casas decimais
                .multiply(BigDecimal.valueOf(100));
    }


    @Override
    public void deleteProduct(Long productId) throws ProductException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductException("Product not found"));
        productRepository.delete(product);

    }

    @Override
    public Product updateProduct(Long productId, ProductRequestDTO req) throws ProductException {
        // Busca o produto existente pelo ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found"));

        // Atualiza os campos somente se os valores no DTO não forem nulos
        if (req.title() != null) {
            product.setTitle(req.title());
        }
        if (req.description() != null) {
            product.setDescription(req.description());
        }
        if (req.mrpPrice() != null) {
            product.setMrpPrice(req.mrpPrice());
        }
        if (req.sellingPrice() != null) {
            product.setSellingPrice(req.sellingPrice());
        }
        if (req.discountPercentage() != null) {
            product.setDiscountPercentage(req.discountPercentage());
        }
        if (req.color() != null) {
            product.setColor(req.color());
        }
        if (req.quantity() != null) {
            product.setQuantity(req.quantity());
        }
        if (req.images() != null && !req.images().isEmpty()) {
            product.setImages(req.images());
        }
        if (req.numRating() != null) {
            product.setNumRating(req.numRating());
        }
        if (req.sizes() != null && !req.sizes().isEmpty()) {
            product.setSizes(req.sizes());
        }
        if (req.active()) {
            product.setActive(req.active());
        }

        // Atualiza a data de modificação
        product.setUpdatedDate(LocalDateTime.now());

        // Salva as alterações no banco de dados
        return productRepository.save(product);
    }


    @Override
    public Product findProductById(Long productId) throws ProductException {
        return productRepository.findById(productId).orElseThrow(() -> new ProductException("Product not found"));
    }


    @Override
    public List<Product> searchProducts(String query) {
        return productRepository.searchProducts(query);
    }

    @Override
    public Page<Product> getAllProducts(String category, String brand, String colors, String sizes,
                                        BigDecimal minPrice, BigDecimal maxPrice, BigDecimal minDiscount,
                                        String sort, String stock, Integer pageNumber) {

        Specification<Product> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (category != null) {
                Join<Product, Category> categoryJoin = root.join("category");
                predicates.add(criteriaBuilder.equal(categoryJoin.get("name"), category));
            }

            if (brand != null) {
                Join<Product, String> brandJoin = root.join("brand");
                predicates.add(criteriaBuilder.equal(brandJoin.get("name"), brand));
            }

            if (colors != null && !colors.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("color"), "%" + colors + "%"));
            }

            if (sizes != null && !sizes.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("sizes"), "%" + sizes + "%"));
            }

            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"), maxPrice));
            }

            if (minDiscount != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercentage"), minDiscount));
            }

            if (stock != null) {
                predicates.add(criteriaBuilder.equal(root.get("stock"), stock));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Pageable pageable = PageRequest.of(
                pageNumber != null ? pageNumber : 0,
                10,
                sort != null && sort.equalsIgnoreCase("price_low")
                        ? Sort.by("sellingPrice").ascending()
                        : sort != null && sort.equalsIgnoreCase("price_high")
                        ? Sort.by("sellingPrice").descending()
                        : Sort.unsorted()
        );

        return productRepository.findAll(spec, pageable);
    }


    @Override
    public List<Product> findProductsBySellerId(Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

}
