package com.ll.domain.order.dto;

import com.ll.domain.order.entity.Order;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record OrderDto(
        int id,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        int orderCount,
        String productName,
        int totalPrice,
        String address,
        boolean deliveryStatus,
        Order.OrderStatus order_status,
//        int productId,
        String email
//        List<OrderItemDto> orderItems
){
    public OrderDto(Order order) {
        this(
                order.getId(),
                order.getCreateDate(),
                order.getModifyDate(),
                order.getOrder_count(),
                order.getProduct_name(),
                order.getTotal_price(),
                order.getAddress(),
                order.isDelivery_status(),
                order.getOrder_status(),
//                order.getProduct().getId(),
                order.getEmail()
                // order.getOrderItems()
                // .stream()
                // .map(OrderItemDto::new)
                // .collect(Collectors.toList())
        );
    }
}