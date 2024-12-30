package com.buzzbridge.ecommerce_boilerplate.product_service.dto;

import java.math.BigDecimal;

public record ProductResponse(String id, String name, String description, String skuCode, BigDecimal price) {
}
