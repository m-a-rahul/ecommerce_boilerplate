package com.buzzbridge.ecommerce_boilerplate.product_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(value="product")
public class Product {
    private String id;
    private String name;
    private String description;
    private String skuCode;
    private BigDecimal price;
}
