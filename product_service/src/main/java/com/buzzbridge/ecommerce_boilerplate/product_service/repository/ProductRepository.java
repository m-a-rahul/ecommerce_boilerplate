package com.buzzbridge.ecommerce_boilerplate.product_service.repository;

import com.buzzbridge.ecommerce_boilerplate.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
