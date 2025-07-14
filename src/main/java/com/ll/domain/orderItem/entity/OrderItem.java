package com.ll.domain.orderItem.entity;

import com.ll.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem extends BaseEntity {
    private int order_count;
    private int product_price;
    private int total_price;
    private int order_id;
    private int product_id;
}
