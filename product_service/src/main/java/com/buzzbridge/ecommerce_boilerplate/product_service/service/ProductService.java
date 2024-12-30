package com.buzzbridge.ecommerce_boilerplate.product_service.service;

import com.buzzbridge.ecommerce_boilerplate.product_service.dto.ProductRequest;
import com.buzzbridge.ecommerce_boilerplate.product_service.dto.ProductResponse;
import com.buzzbridge.ecommerce_boilerplate.product_service.model.Product;
import com.buzzbridge.ecommerce_boilerplate.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .skuCode(productRequest.skuCode())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product Created Successfully");
        return new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getSkuCode(),product.getPrice());
    }

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getSkuCode(),product.getPrice()))
                .toList();
    }
}
