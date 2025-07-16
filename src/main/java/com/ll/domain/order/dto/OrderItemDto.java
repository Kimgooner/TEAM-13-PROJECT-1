package com.ll.domain.order.dto;

import com.ll.domain.order.entity.OrderItem;

public record OrderItemDto(
        int id,
        String productName,
        int quantity,
        int productPrice,
        int totalPrice
) {
    public OrderItemDto(OrderItem orderItem) {
        this(
                orderItem.getId(),
                orderItem.getProduct().getProductName(),
                orderItem.getQuantity(),
                orderItem.getProduct_price(),
                orderItem.getTotal_price()
        );
    }
}
