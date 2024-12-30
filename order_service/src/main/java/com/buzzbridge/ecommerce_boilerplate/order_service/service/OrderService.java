package com.buzzbridge.ecommerce_boilerplate.order_service.service;


import com.buzzbridge.ecommerce_boilerplate.order_service.client.InventoryClient;
import com.buzzbridge.ecommerce_boilerplate.order_service.dto.OrderRequest;
import com.buzzbridge.ecommerce_boilerplate.order_service.event.OrderPlacedEvent;
import com.buzzbridge.ecommerce_boilerplate.order_service.model.Order;
import com.buzzbridge.ecommerce_boilerplate.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(),orderRequest.quantity());
        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);

            var orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
            orderPlacedEvent.setEmail(orderRequest.userDetails().email());
            orderPlacedEvent.setFirstName("Rahul");
            orderPlacedEvent.setLastName("Anto");
            log.info("Start- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);
        } else {
            throw new RuntimeException("Product with SkuCode "+orderRequest.skuCode()+" is not in stock");
        }
    }
}
